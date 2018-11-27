package Acorn.Stove;
import Acorn.Pestle.*;
import Acorn.WTFerror;

class PrecRank extends TokenList {
  // this is called a comb sort
  ArrayList<Integer> list = new ArrayList<Integer>();
  PrecRank(TokenList t) {
    int size = t.size();
    for (int i = 0; i < size; i++) {
      Token current = t.get(i);
      int listSize = this.list.size();
      do {

      } while (false);
    }
  }
}
public class Parser {
  int length;
  Expression AST;
  TokenList tokens;
  public Parser(TokenList tokens) {
    this.tokens = tokens;
    this.length = tokens.size();
    for (let i = 0; i < this.length; i++) {
      Token current = tokens.get(i);
      LowestPrecSingleton.add(i, current.prec());
    }
    
  }
  private ParenExpression parenLeft(int bLeft) {
    for (int i = bLeft; i < )
  }
}
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
