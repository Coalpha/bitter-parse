package Acorn.Pestle;
import java.util.ArrayList;

public class TokenList extends ArrayList<Token> {
  static final long serialVersionUID = 0xbe4dead;
  @Override
  public String toString() {
    String res = "";
    int l = this.size();
    for (int i = 0; i < l; i++) {
      res += this.get(i).toString() + ",\n";
    }
    return res;
  }
};
