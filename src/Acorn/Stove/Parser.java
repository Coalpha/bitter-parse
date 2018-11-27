package Acorn.Stove;
import Acorn.Pestle.*;
import Acorn.WTFerror;

public class Parser {
  int length;
  Expression AST;
  TokenList tokens;
  public Parser(TokenList tokens) {
    this.tokens = tokens;
    this.length = tokens.size();
    for (let i = 0; i < this.length; i++) {
      Token current = tokens.get(i);
      
    }
  }
  private ParenExpression parenLeft(int bLeft) {
    for (int i = bLeft; i < )
  }
}
