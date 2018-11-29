package Acorn.Stove;

import Acorn.Pestle.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Parser {
  int length;
  final TokenList tokens;
  public Expression AST;
  public Parser(TokenList tokens) {
    this.tokens = tokens;
    this.AST = parse2Expression(tokens);
  }
  Expression parse2Expression(TokenList tokens) {
    TokenAndPosition leastPrec = tokens.getLeastPrec();
    if (leastPrec.token.type == TokenTypes.num) {
      return new Literal(leastPrec.token.value);
    }
    return new Literal("0");
  }
  BinopExpression parseUnary(Token op, TokenList right) {
    return new BinopExpression(
      new Literal("0"),
      op.value,
      parse2Expression(right)
    );
  }
  BinopExpression parseBinop(TokenList left, Token center, TokenList right) {
    return new BinopExpression(
      parse2Expression(left),
      center.value,
      parse2Expression(right)
    );
  }
  BinopExpression parseMixedNumber(TokenList left, Token center, TokenList right) {
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
    System.out.println(this.tokens.toString());
    return this.AST.toString();
  }
}

/*
int l = this.size();
for (int i = 0; i < l; i++) {
  new FFSJava("Why do I have to keep using loops everywhere");
}
*/
// 2 + 3 * 2_1/3
/*
BinopExpression {
  binop: +
  left: 2
  right: BinopExpression {
    binop: *
    left: 3
    right: BinopExpression {
      binop: +
      left: 2
      right: BinopExpression {
        binop: /
        left: 1
        right: 3
      }
    }
  } 
}
*/
