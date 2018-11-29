package Acorn.Pestle;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

import Acorn.WTFerror;

public class TokenList extends ArrayList<Token> {
  static final long serialVersionUID = 0xbe4dead;
  Predicate<Token> isWhiteSpace = (Token t) -> t.type == TokenTypes.space;
  void verifyParens() {
    int l = this.size();
    int lParenCount = 0;
    int rParenCount = 0;
    for (int i = 0; i < l; i++) {
      Token currentToken = this.get(i);
      if (currentToken.type == TokenTypes.parenL) {
        lParenCount++;
      } else if (currentToken.type == TokenTypes.parenR) {
        rParenCount++;
      }
    }
    if (lParenCount > rParenCount) {
      throw new WTFerror("Too many left parens!");
    }
    if (rParenCount > lParenCount) {
      throw new WTFerror("Too many right parens!");
    }
  }
  public int findMatchingParen(int indexOfCurrentParen) {
    verifyParens();
    // TO REMOVE!!!!
    int l = this.size();
    int parenScope = 0;
    int matchingParenIndex = -1;
    for (int i = 0; i < l; i++) {
      Token currentToken = this.get(i);
      if (currentToken.type == TokenTypes.parenL) {
        parenScope++;
        continue;
      } else if (currentToken.type == TokenTypes.parenR) {
        parenScope--;
        if (parenScope == 0) {
          matchingParenIndex = i;
        }
      }
      if (parenScope > 0) {
        continue;
      }
    }
    return matchingParenIndex;
  }
  public TokenAndPosition getLeastPrec() {
    verifyParens();
    Token t = new Token(
      "leastPrecStartingToken",
      new TokenType("lpst", 99)
    );
    int idx = 0;
    int l = this.size();
    int parenScope = 0;
    for (int i = 0; i < l; i++) {
      Token currentToken = this.get(i);
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
    TokenList a = new TokenList();
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
  public void stripWhiteSpace() {
    this.removeIf(this.isWhiteSpace);
  }
  public void shiftSOF() {
    if (this.get(0).type == TokenTypes.sof) {
      this.remove(0);
    }
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
