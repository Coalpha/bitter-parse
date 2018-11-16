package Acorn.Pestle;

public class Tokenizer {
  static Boolean isNumber(Character c) {
    return Character.toString(c).matches("\\d");
  }
  String inp;
  Boolean spaceBefore = false;
  TokenList tokens = new TokenList();
  public Tokenizer(String inp) throws UnexpectedCharacter {
    int l = inp.length();
    this.append('s', TokenTypes.sof);
    for (int i = 0; i < l; i++) {
      char c = inp.charAt(i);
      if (isNumber(c)) {
        if (this.spaceBefore) {
          this.append(c, TokenTypes.num);
        } else {
          this.condTypeAppend(c, TokenTypes.num);
        }
      } else if (c == '+' || c == '-') {
        this.append(c, TokenTypes.plusMin);
        // yes, I know this is repetitive
      } else if (c == '*') {
        this.append(c, TokenTypes.star);
      } else if (c == '/') {
        this.append(c, TokenTypes.slash);
      } else if (c == '(') {
        this.append(c, TokenTypes.parenL);
      } else if (c == ')') {
        this.append(c, TokenTypes.parenR);
      } else if (c == '^') {
        this.append(c, TokenTypes.carrot);
      } else if (c == '_') {
        this.append(c, TokenTypes.underscore);
      } else if (c == ' ') {
        this.spaceBefore = true;
        continue;
      } else {
        throw new UnexpectedCharacter(c);
      }
      this.spaceBefore = false;
      // tfw switch only works on enums in Java
      // For what it's worth, it probably makes switch more performant
      // but since Java's a snail, it doesn't really make sense.
    }
    this.append('e', TokenTypes.eof);
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
    if (lToken.label().equals(t.label)) {
      lToken.append(c);
    } else {
      this.append(c, t);
    }
  }
  public TokenList getTokens() {
    return this.tokens;
  }
}
