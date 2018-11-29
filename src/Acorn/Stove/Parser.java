package Acorn.Stove;

import Acorn.Pestle.*;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Parser {
  int length;
  final TokenList tokens;
  public Expression AST;
  public Parser(TokenList tokens) {
    tokens.stripWhiteSpace();
    this.tokens = tokens;
    this.AST = parse2Expression(tokens);
  }
  Expression parse2Expression(TokenList tokens) {
    TokenAndPosition leastPrec = tokens.getLeastPrec();
    // System.out.println("<Acorn.Stove.Parser.parse2Expression>");
    // System.out.println(leastPrec);
    // System.out.println("</Acorn.Stove.Parser.parse2Expression>");
    if (leastPrec.token.type == TokenTypes.num) {
      return new Literal(leastPrec.token.value);
    }
    if (leastPrec.token.type == TokenTypes.parenL) {
      return parenLeft(tokens.slice(leastPrec.index + 1));
    }
    BinopCollection collection = tokens.binopSplit(leastPrec.index);
    if (leastPrec.token.type.binop) {
      return parseBinop(collection.left, collection.binop, collection.right);
    }
    if (leastPrec.token.type == TokenTypes.underscore) {
      return parseMixedNumber(collection.left, collection.binop, collection.right);
    }
    return new Literal("Parser cant deal with it yet");
  }
  Expression parenLeft(TokenList tokens) {
    // System.out.println("<Acorn.Stove.Parser.parenLeft>");
    // System.out.print(tokens);
    // System.out.println("</Acorn.Stove.Parser.parenLeft>");
    int l = tokens.size();
    int scopeLevel = 0;
    int endParen = -1;
    for (int i = 0; i < l; i++) {
      Token current = tokens.get(i);
      if (current.type == TokenTypes.parenL) {
        scopeLevel++;
      } else if (current.type == TokenTypes.parenR) {
        if (endParen == -1) {
          // if we haven't found an endParen on the same scope
          if (scopeLevel == 0) {
            endParen = i;
          } else {
            scopeLevel--;
          }
        } else {
          // wait there's an extra one?
          throw new UnexpectedToken("extra )");
        }
      } else if (current.type == TokenTypes.eof && endParen == -1) {
        throw new UnexpectedToken("end of file");
      }
    }
    return parse2Expression(tokens.slice(0, endParen));
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
  @Override
  public String toString() {
    // System.out.println("<Acorn.Stove.Parser.toString>");
    // System.out.print(this.tokens);
    // System.out.println("</Acorn.Stove.Parser.toString>");
    return this.AST.toString();
  }
}
