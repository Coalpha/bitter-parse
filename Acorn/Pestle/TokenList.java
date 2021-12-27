package Acorn.Pestle;

import Acorn.AcornError;
import java.util.ArrayList;
import java.util.function.Predicate;

public class TokenList extends ArrayList<Token> {
  static final long serialVersionUID = 0xbe4dead;
  // I have to declare this otherwise the compiler complains
  Predicate<Token> isWhiteSpace = (Token t) -> t.type == TokenTypes.space;
  public void verifyParens() {
    // verifyParens is only called in Acorn.Stove.Parser
    // it's only called once per parse.
    // this function keeps track of the left parens and right parens
    // if they aren't the same the user has emitted a bad
    int lParenCount = 0;
    int rParenCount = 0;
    for (Token currentToken : this) {
      // iterator for loop syntax
      // ArrayList<T> implements Iterable :)
      if (currentToken.type == TokenTypes.parenL) {
        lParenCount++;
      } else if (currentToken.type == TokenTypes.parenR) {
        rParenCount++;
      }
    }
    if (lParenCount > rParenCount) {
      throw new AcornError("Too many left parens!");
    }
    if (rParenCount > lParenCount) {
      throw new AcornError("Too many right parens!");
    }
  }
  public int findMatchingParen(int indexOfCurrentParen) {
    int l = this.size();
    // cache size
    int parenScope = 0;
    // parenScope keeps track of how many parens deep the for loop is
    int matchingParenIndex = -1;
    // it starts at -1
    // why don't I start it at 0?
    // what if the matching paren is at 0?
    // (it shouldn't be, that's not how parens work but whatever. Saftey)
    for (int i = indexOfCurrentParen; i < l; i++) {
      // don't start i from 0
      // that caused bugs
      Token currentToken = this.get(i);
      if (currentToken.type == TokenTypes.parenL) {
        parenScope++;
        // going one paren deeper
        continue;
        // continue so the for loop doesn't waste time checking the other types
      }
      if (currentToken.type == TokenTypes.parenR) {
        parenScope--;
        // don't continue!
        // fall through to next if statement
      }
      if (parenScope == 0) {
        matchingParenIndex = i;
        break;
        // stop looping, we've found the index
      }
    }
    return matchingParenIndex;
  }
  public TokenAndPosition findFromIndex(int start, Predicate<Token> test) {
    int l = this.size();
    // cache
    for (int i = start; i < l; i++) {
      Token current = this.get(i);
      if (test.test(current)) {
        return new TokenAndPosition(current, i);
      }
      if (current.type == TokenTypes.parenL) {
        i = this.findMatchingParen(i);
        continue;
      }
    }
    return new TokenAndPosition(Token.NA, -1);
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
    int s = this.size();
    for (int i = 0; i < s; i++) {
      Token current = this.get(i);
      res += i + ": " + current + ",\n";
    }
    return res;
  }
};
