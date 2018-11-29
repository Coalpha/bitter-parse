package Acorn.Stove;

import Acorn.WTFerror;
import Acorn.Pestle.*;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Parser {
  int length;
  final TokenList tokens;
  public Expression AST;
  public Parser(TokenList tokens) {
    tokens.shiftSOF();
    tokens.stripWhiteSpace();
    this.tokens = tokens;
    this.AST = parse2Expression(tokens);
  }
  Expression parse2Expression(TokenList tokens) {
    // System.out.println("<Acorn.Stove.Parser.parse2Expression>");
    // System.out.println(tokens);
    if (tokens.size() == 0) {
      throw new WTFerror("Cannot parse TokenList of size 0!");
    }
    TokenAndPosition leastPrec = getLeastPrec(tokens);
    // System.out.println("leastPrec:");
    // System.out.println(leastPrec);
    // System.out.println("</Acorn.Stove.Parser.parse2Expression>");
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
    // System.out.println("<Acorn.Stove.Parser.parseUnary>");
    // System.out.println("op:\n" + op);
    // System.out.print("right:\n" + right);
    // System.out.println("</Acorn.Stove.Parser.parseUnary>");
    return new BinopExpression(
      new Literal("0"),
      op.value,
      parse2Expression(right)
    );
  }
  BinopExpression parseBinop(TokenList left, Token center, TokenList right) {
    // System.out.println("<Acorn.Stove.Parser.parseBinop>");
    // System.out.print("left:\n" + left);
    // System.out.println("center:\n" + center);
    // System.out.print("right:\n" + right);
    // System.out.println("</Acorn.Stove.Parser.parseBinop>");
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
    // System.out.println("<Acorn.Stove.Parser.parseMixedNumber>");
    // System.out.print("left:\n" + left);
    // System.out.println("center:\n" + center);
    // System.out.print("right:\n" + right);
    // System.out.println("</Acorn.Stove.Parser.parseMixedNumber>");
    BinopExpression exRight = (BinopExpression) parse2Expression(right);
    if (!exRight.binop.equals("/")) {
      throw new UnexpectedToken(exRight.binop, "/");
    }
    return new BinopExpression(
      parse2Expression(left),
      "+",
      exRight
    );
  }
  public TokenAndPosition getLeastPrec(TokenList tokens) {
    tokens.verifyParens();
    Token t = new Token(
      "leastPrecStartingToken",
      new TokenType("lpst", 99)
    );
    int idx = 0;
    int l = tokens.size();
    int parenScope = 0;
    boolean mixedNumberStart = false;
    for (int i = 0; i < l; i++) {
      Token currentToken = tokens.get(i);
      if (currentToken.type == TokenTypes.parenL) {
        parenScope++;
        continue;
      } else if (currentToken.type == TokenTypes.parenR) {
        parenScope--;
      }
      if (parenScope > 0) {
        continue;
      }
      int currentPrec = currentToken.prec();
      if (
        currentPrec > 0
        && currentPrec < t.prec()
        && !(
          t.type.binop
          && currentToken.type == TokenTypes.plusMin
        )
        // so that something like + - actually parses
      ) {
        if (currentToken.type == TokenTypes.underscore) {
          mixedNumberStart = true;
        } else if (
          mixedNumberStart
          && currentToken.type == TokenTypes.slash
        ) {
          mixedNumberStart = false;
          continue;
        }
        t = currentToken;
        idx = i;
      }
    }
    return new TokenAndPosition(t, idx);
  }
  @Override
  public String toString() {
    // System.out.println("<Acorn.Stove.Parser.toString>");
    // System.out.print(this.tokens);
    // System.out.println("</Acorn.Stove.Parser.toString>");
    return this.AST.toString();
  }
}
