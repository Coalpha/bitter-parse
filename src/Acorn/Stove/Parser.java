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
  }
  /*
  Expression parse2Expression(TokenList tokens) {

  }
  */
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
  /* TODO
  BinopExpression parse_(TokenList left, Token center, TokenList right) {
    // Expression exLeft = parse2Expression(left);
    // int l = this.size();
    // TokenAndPosition binop = right.nextBinop();
    // if (binop.token.type != TokenTypes.slash) {
    //   throw new UnexpectedToken(binop.value, "/");
    // }
    // return new BinopExpression(exLeft, "+", parseBinop(left, binop, right));
  }
  */
  @Override
  public String toString() {
    return "foobar";
    // return this.AST.toString();
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
