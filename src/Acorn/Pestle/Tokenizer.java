package Acorn.Pestle;

public class Tokenizer {
  static Boolean isNumber(Character c) {
    return Character.toString(c).matches("\\d");
  }
  String inp;
  Boolean spaceBefore = false;
  TokenList tokens = new TokenList();
  public Tokenizer(String inp) {
    int l = inp.length();
    this.append('s', TokenTypes.sof);
    for (int i = 0; i < l; i++) {
      char c = inp.charAt(i);
      if (isNumber(c)) {
        if (this.spaceBefore) {
          append(c, TokenTypes.num);
        } else {
          this.condTypeAppend(c, TokenTypes.num);
        }
      } else if (c == '+' || c == '-') {
        append(c, TokenTypes.plusMin);
      } else if (c == '*') {
        append(c, TokenTypes.star);
      } else if (c == '/') {
        append(c, TokenTypes.slash);
      } else if (c == '(') {
        append(c, TokenTypes.parenL);
      } else if (c == ')') {
        append(c, TokenTypes.parenR);
      } else if (c == '_') {
        append(c, TokenTypes.underscore);
      } else if (c == ' ') {
        this.spaceBefore = true;
        continue;
        // continue otherwise this.spaceBefore is set to false
      } else {
        throw new UnexpectedCharacter(c);
      }
      this.spaceBefore = false;
    }
    append('e', TokenTypes.eof);
  }
  Token getLastToken() {
    return this.tokens.get(this.tokens.size() - 1);
  }
  void append(char c, TokenType t) {
    this.tokens.add(new Token(c, t));
  }
  void condTypeAppend(char c, TokenType t) {
    // if the token type / lable is the same as the last
    // token on the TokenList, then merge cToken and lToken
    // otherwise, just append it to the TokenList
    Token lToken = this.getLastToken();
    if (lToken.type == t) {
      lToken.append(c);
    } else {
      append(c, t);
    }
  }
  public TokenList getTokens() {
    return this.tokens;
  }
}
