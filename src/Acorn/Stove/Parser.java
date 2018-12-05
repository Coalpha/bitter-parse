package Acorn.Stove;

import Acorn.AcornError;
import Acorn.Pestle.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

// this is the only file in Acorn.Stove that actually needs explaining
// and boy, it needs a lot of explaining
public class Parser {
  int length;
  final TokenList tokens;
  public Expression AST;
  boolean verbose = true;
  // I like to keep static things at the top of the class
  static boolean isSlash(Token token) {
    // this function makes no sense without context
    // just remember that it exists
    return token.type == TokenTypes.slash;
  }
  public Parser(TokenList tokens) {
    // the way that Parser works is that you pass the constructor a TokenList
    // it parses it and then you store the new Parser in a variable and get .AST from it
    // String --> Tokenizer --> TokenList --> Parser --> AST --> Squirrel
    // we're at the parser bit
    tokens.shiftSOF();
    // remove the start of file token
    // it was just there as a placeholder
    tokens.verifyParens();
    // make sure that the number of left parens is == to right parens
    tokens.stripWhiteSpace();
    // remove the whitespace
    // the parser doesn't care about whitespace
    this.tokens = tokens;
    // I don't think this is actually needed in production
    // in debug, it's real helpful though
    this.AST = parse2Expression(tokens);
  }
  Expression parse2Expression(TokenList tokens) {
    if (this.verbose) {
      // yes, this is slower than stripping debug checks
      // it's also easier to debug because all you have to do is change this.verbose to true
      // you can ignore everything inside if (this.verbose)
      System.out.println("<Acorn.Stove.Parser.parse2Expression>");
      System.out.println(tokens);
    }
    if (tokens.size() == 0) {
      throw new AcornError("Cannot parse TokenList of size 0!");
    }
    TokenAndPosition leastPrec = getLeastPrec(tokens);
    // returns a TokenAndPosition of the Token that has the least precedence
    if (this.verbose) {
      System.out.println("leastPrec:");
      System.out.println(leastPrec);
      System.out.println("</Acorn.Stove.Parser.parse2Expression>");
    }
    if (leastPrec.token.type == TokenTypes.num) {
      // if it's a number, parse it as such
      return new Literal(leastPrec.token.value);
    }
    if (leastPrec.token.type == TokenTypes.unary) {
      return parseUnary(leastPrec.token, tokens.slice(leastPrec.index + 1));
    }
    // at this point it can only be a binary or ternary operator so split the tokens into three pieces
    BinopCollection collection = tokens.binopSplit(leastPrec.index);
    // recall Acorn.Pestle.BinopCollection
    if (leastPrec.token.type.binop) {
      // if it's a binop (underscores are not binops)
      // example binop tokens:
      // +, -, *, /
      return parseBinop(collection.left, collection.binop, collection.right);
    }
    if (leastPrec.token.type == TokenTypes.underscore) {
      return parseMixedNumber(collection.left, collection.binop, collection.right);
    }
    if (tokens.get(0).type == TokenTypes.parenL) {
      int matchingParen = tokens.findMatchingParen(0);
      return parse2Expression(tokens.slice(1, matchingParen));
    }
    return new Literal("Oh no");
  }
  public TokenAndPosition getLeastPrec(TokenList tokens) {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.getLeastPrec>");
      System.out.print(tokens);
    }
    // initialize the starting token
    TokenAndPosition leastPrec = new TokenAndPosition(
      new Token(
        "leastPrecStartingToken",
        new TokenType("lpst", 99) // that 99 is the precedence
        // ANY real token should have a lower precedence than that
      ),
      0 // this argument is actually going to new TokenAndPosition
    );
    int l = tokens.size();
    for (int i = 0; i < l; i++) {
      Token previous;
      if (i == 0) {
        // if there's no previous
        previous = new Token("", TokenTypes.sof);
      } else {
        previous = tokens.get(i - 1);
        // get the previous token
      }
      Token current = tokens.get(i);
      if (current.type == TokenTypes.parenL) {
        // if it's a left paren
        i = tokens.findMatchingParen(i);
        // find the matching right paren
        // remember, we already know that one exists since Parser.main calls tokens.verifyParens
        if (this.verbose) {
          System.out.println("parenL at: " + i);
          System.out.println("found matching paren at: " + i);
        }
        continue;
      }
      int currentPrec = current.prec();
      // prec() is shorthand for current.type.prec
      if (current.type == TokenTypes.plusMin) {
        if (this.verbose) {
          System.out.println("+- or something");
        }
        if (previous.type.binop) {
          // if the token list looks like:
          // 1 +- 1 / 2
          //    ^ current token
          if (this.verbose) {
            System.out.println("Skipping");
          }
          // don't consider the current token as a least precedence candidate
          continue;
        }
      }
      if (
        currentPrec > 0 // if the TokenType has 0 precedence it's not a candidate
        && currentPrec < leastPrec.token.prec()
      ) {
        // the current token's precedence is greater than 0
        // and less than the previously lowest precedence token
        if (this.verbose) {
          System.out.println("reached final with current token:");
          System.out.println(current);
        }
        leastPrec = new TokenAndPosition(current, i);
        // current is a token, i is the position within the TokenList
      }
      if (current.type == TokenTypes.underscore) {
        TokenAndPosition nextSlash = tokens.findFromIndex(i, Parser::isSlash);
        if (this.verbose) {
          System.out.println("underscore");
          System.out.println("the next slash is");
          System.out.println(nextSlash);
        }
        if (nextSlash.index == -1) {
          throw new AcornError("Expected \"/\" but found nothing!");
        }
        i = nextSlash.index;
      }
    }
    if (this.verbose) {
      System.out.println("</Acorn.Stove.Parser.getLeastPrec>");
    }
    return leastPrec;
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
  @Override
  public String toString() {
    if (this.verbose) {
      System.out.println("<Acorn.Stove.Parser.toString>");
      System.out.print(this.tokens);
      System.out.println("</Acorn.Stove.Parser.toString>");
    }
    return this.AST.toString();
  }
}
