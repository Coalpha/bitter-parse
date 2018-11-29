package Acorn.Pestle;
import java.util.List;
import java.util.ArrayList;
public class TokenList extends ArrayList<Token> {
  static final long serialVersionUID = 0xbe4dead;
  public TokenAndPosition getLeastPrec() {
    Token t = new Token(
      "leastPrecStartingToken",
      new TokenType("lpst", 99)
    );
    int idx = 0;
    int l = this.size();
    for (int i = 0; i < l; i++) {
      Token currentToken = this.get(i);
      int currentPrec = currentToken.prec();
      if (currentPrec > 0 && currentPrec < t.prec()) {
        t = currentToken;
        idx = i;
      }
    }
    return new TokenAndPosition(t, idx);
  }
  public TokenAndPosition nextBinop() {
    int l = this.size();
    for (int i = 0; i < l; i++) {
      Token current = this.get(i);
      if (current.type.binop) {
        return new TokenAndPosition(current, i);
      }
    }
    return new TokenAndPosition(Token.NA, 0);
  }
  public TokenList slice(int start) {
    return this.slice(start, this.size());
  }
  public TokenList slice(int start, int end) {
    TokenList a = (TokenList) new ArrayList<Token>(end - start);
    a.addAll(this.subList(start, end));
    return a;
  }
  public BinopCollection binopSplit(int binopIndex) {
    return new BinopCollection(
      this.slice(0, binopIndex),
      this.get(binopIndex),
      this.slice(binopIndex + 1)
    );
  }
  @Override
  public String toString() {
    String res = "";
    int l = this.size();
    for (int i = 0; i < l; i++) {
      res += this.get(i).toString() + ",\n";
    }
    // thanks java, I've literally written the same loop
    // since closures are lengthy, there's no way to shorten this
    return res;
  }
};
