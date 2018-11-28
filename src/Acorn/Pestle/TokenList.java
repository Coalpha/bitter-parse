package Acorn.Pestle;
import java.util.List;
import java.util.ArrayList;
public class TokenList extends ArrayList<Token> {
  static final long serialVersionUID = 0xbe4dead;
  public static TokenList fromList(List<Token> l) {

    TokenList a = (TokenList) new ArrayList<Token>(l.size());
    a.addAll(l);
    return a;
  }
  public TokenAndPosition getLeastPrecIndex() {
    Token t = Token.NA;
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
  public BinaryCollection binopSplit(int binopIndex) {
    return new BinaryCollection(
      TokenList.fromList(this.subList(0, binopIndex)),
      this.get(binopIndex),
      TokenList.fromList(this.subList(binopIndex + 1, this.size()))  
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
