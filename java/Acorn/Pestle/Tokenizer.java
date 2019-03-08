package Acorn.Pestle;

public class Tokenizer {
  // here's where the tokenizing happens!
  static Boolean isNumber(Character c) {
    return Character.toString(c).matches("\\d");
  }
  String inp;
  TokenList tokens = new TokenList();
  public Tokenizer(String inp) {
    // the way that the tokenizer is initiated is not by calling a static method
    // it's `new Tokenizer("12 + 34")` or something like that
    // Tokenizer is only used by an outside package so the constructor must be public
    int l = inp.length();
    // fetching length is expensive, cache it.
    append('s', TokenTypes.sof);
    for (int i = 0; i < l; i++) {
      // this for loop iterates through every character within the provided string
      char c = inp.charAt(i);
      // get the current character
      if (isNumber(c)) {
        // the character is a Number character. Confusing right?
        // basically the data is a character but the character is like '2' or something
        condTypeAppend(c, TokenTypes.num);
        // check if the last token was also a Number
      } else if (c == '+' || c == '-') {
        // if it's plus or minus
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
        // why have space be a thing?
        // I don't want `12 34` to parse to [1234]
        append(c, TokenTypes.space);
      } else {
        // otherwise the character is a ???!
        // so just throw an error.
        throw new UnexpectedCharacter(c);
      }
      // this is what you get when you can only switch enums
      // for loop continues
    }
    append('e', TokenTypes.eof);
    // append the end of file token
    // useful when dealing with parens 
  }
  Token getLastToken() {
    return this.tokens.get(this.tokens.size() - 1);
  }
  void condTypeAppend(char c, TokenType t) {
    // if the token type / lable is the same as the last
    // token on the TokenList, then merge cToken and lToken
    // otherwise, just append it to the TokenList
    // this may seem pointless but think about if the last token is a Number
    // if the last token is 1 and the current token is 2, it should append to the last token
    // otherwise it'll add another Number token to the TokenList
    // good:
    /*
      [12]
    */
    // bad:
    /*
      [1, 2]
    */
    // for most token types, this doesn't matter, hence this.append
    Token lToken = getLastToken();
    if (lToken.type == t) {
      lToken.append(c);
    } else {
      append(c, t);
    }
  }
  void append(char c, TokenType t) {
    // just append the token, no need to check if it's the same type
    this.tokens.add(new Token(c, t));
  }
  public TokenList getTokens() {
    // this function is used once within Acorn.Tree
    // all that it's used for is to pass the TokenList to the parser
    return this.tokens;
  }
}
