package Acorn.Stove;

import Acorn.WTFerror;
import Acorn.Pestle.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Parser {
  int length;
  final TokenList tokens;
  public Expression AST;
  boolean verbose = true;
  public Parser(TokenList tokens) {
    tokens.shiftSOF();
    tokens.verifyParens();
    tokens.stripWhiteSpace();
    this.tokens = tokens;
    this.AST = parse2Expression(tokens);
  }
  Expression parse2Expression(TokenList tokens) {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.parse2Expression>");
      System.out.println(tokens);
    }
    if (tokens.size() == 0) {
      throw new WTFerror("Cannot parse TokenList of size 0!");
    }
    TokenAndPosition leastPrec = getLeastPrec(tokens);
    if (this.verbose) {
      System.out.println("leastPrec:");
      System.out.println(leastPrec);
      System.out.println("</Acorn.Stove.Parser.parse2Expression>");
    }
    if (leastPrec.token.type == TokenTypes.num) {
      return new Literal(leastPrec.token.value);
    }
    BinopCollection collection = tokens.binopSplit(leastPrec.index);
    if (leastPrec.token.type.binop) {
      return parseBinop(collection.left, collection.binop, collection.right);
    }
    if (leastPrec.token.type == TokenTypes.underscore) {
      return parseMixedNumber(collection.left, collection.binop, collection.right);
    }
    if (tokens.get(0).type == TokenTypes.parenL) {
      int matchingParen = tokens.findMatchingParen(0);
      return parse2Expression(tokens.slice(1, matchingParen));
    }
    return new Literal("Parser cant deal with it yet");
  }
  BinopExpression parseUnary(Token op, TokenList right) {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.parseUnary>");
      System.out.println("op:\n" + op);
      System.out.print("right:\n" + right);
      System.out.println("</Acorn.Stove.Parser.parseUnary>");
    }
    return new BinopExpression(
      new Literal("0"),
      op.value,
      parse2Expression(right)
    );
  }
  BinopExpression parseBinop(TokenList left, Token center, TokenList right) {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.parseBinop>");
      System.out.print("left:\n" + left);
      System.out.println("center:\n" + center);
      System.out.print("right:\n" + right);
      System.out.println("</Acorn.Stove.Parser.parseBinop>");
    }
    if (
      (
        left.size() == 1
        && left.get(0).type == TokenTypes.sof
      )
      || left.size() == 0
    ) {
      return parseUnary(center, right);
    }
    return new BinopExpression(
      parse2Expression(left),
      center.value,
      parse2Expression(right)
    );
  }
  BinopExpression parseMixedNumber(TokenList left, Token center, TokenList right) {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.parseMixedNumber>");
      System.out.print("left:\n" + left);
      System.out.println("center:\n" + center);
      System.out.print("right:\n" + right);
      System.out.println("</Acorn.Stove.Parser.parseMixedNumber>");
    }
    return new BinopExpression(
      parse2Expression(left),
      "+",
      parse2Expression(right)
    );
  }
  static boolean nextSlash(Token token) {
    return token.type == TokenTypes.slash;
  }
  public TokenAndPosition getLeastPrec(TokenList tokens) {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.getLeastPrec>");
    }
    TokenAndPosition leastPrec = new TokenAndPosition(
      new Token(
        "leastPrecStartingToken",
        new TokenType("lpst", 99)
      ),
      0
    );
    int l = tokens.size();
    for (int i = 0; i < l; i++) {
      Token previous;
      if (i == 0) {
        previous = leastPrec.token;
      } else {
        previous = tokens.get(i - 1);
      }
      Token current = tokens.get(i);
      if (current.type == TokenTypes.parenL) {
        if (this.verbose) {
          System.out.println("parenL");
          i = tokens.findMatchingParen(i);
          System.out.println("found matching paren at: " + i);
        }
        continue;
      }
      int currentPrec = current.prec();
      if (current.type == TokenTypes.plusMin && previous.type.binop) {
        if (this.verbose) {
          System.out.println("+- or something");
        }
        continue;
      }
      if (current.type == TokenTypes.underscore) {
        TokenAndPosition nextSlash = tokens.find(Parser::nextSlash);
        if (this.verbose) {
          System.out.println("underscore");
          System.out.println("the next slash is");
          System.out.println(nextSlash);
        }
        if (nextSlash.index == -1) {
          throw new WTFerror("Expected \"/\" but found nothing!");
        }
      }
      if (
        currentPrec > 0
        && currentPrec < leastPrec.token.prec()
      ) {
        leastPrec = new TokenAndPosition(current, i);
      }
    }
    if (this.verbose) {
      System.out.println("</Acorn.Stove.Parser.getLeastPrec>");
    }
    return leastPrec;
  }
  @Override
  public String toString() {
    // System.out.println("<Acorn.Stove.Parser.toString>");
    // System.out.print(this.tokens);
    // System.out.println("</Acorn.Stove.Parser.toString>");
    return this.AST.toString();
  }
}
