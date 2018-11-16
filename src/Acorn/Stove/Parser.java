package Acorn.Stove;
import Acorn.Pestle.*;
import Acorn.WTFerror;


public class Parser {
  TokenList tokens;
  int[] subListLimits = new int[2];
  public Parser(TokenList tokens) {
    this.tokens = tokens;
    this.subListLimits[0] = 1; // Skip start of file
    this.subListLimits[1] = getSize();
  }
  private int getSize() {
    return this.tokens.size();
  }
  private int getLimitRight() {
    return this.subListLimits[1];
  }
  private int getLimitLeft() {
    return this.subListLimits[0];
  }
  private Token getToken() {
    return this.tokens.get(getLimitLeft());
  }
  private Token nextToken() {
    this.subListLimits[0]++;
    return this.tokens.get(getLimitLeft() - 1);
  }
  public Expression ParseExpression() {
    int l = getLimitRight();
    int curPrec = 99;
    int curIdx = 0;
    int parens = 0;
    for (int i = getLimitLeft(); i < l; i++) {
      Token cT = this.tokens.get(i);
      if (cT.type.binop > 0 && cT.type.binop <= curPrec && parens == 0) {
        curIdx = i;
        curPrec = cT.type.binop;
      } else if (cT.label().equals("prL")) {
        parens++;
      } else if (cT.label().equals("prR")) {
        parens--;
      }
    }
    if (curPrec < 99) {
      this.subListLimits = new int[]{getLimitLeft(), curIdx};
      Expression left = ParseExpression();
      String bnop = this.tokens.get(curIdx).value;
      String binop = bnop.equals("_") ? "+" : bnop;
      this.subListLimits = new int[]{curIdx + 1, l};
      Expression right = ParseExpression();
      return new BinopExpression(left, binop, right);
    }
    Token cT = nextToken();
    if (cT.label().equals("prL")) {
      return ParseParens();
    }
    if (cT.label().equals("num")) {
      return new Literal(cT.value);
    }
    throw new WTFerror();
  }
  public Expression ParseParens() {
    for (int i = getLimitLeft(); i < getLimitRight(); i++) {
      Token cT = this.tokens.get(i);
      if (cT.label().equals("prL")) {
        this.subListLimits = new int[]{i + 1, getLimitRight()};
        return ParseParens();
      } else if (cT.label().equals("prR")) {
        this.subListLimits = new int[]{getLimitLeft(), i};
        return new ParenExpression(ParseExpression());
      }
    }
    throw new UnexpectedToken(getToken().value, "a paren somewhere");
  }
}
