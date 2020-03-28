package Acorn.Pestle;
import VT100.Colors;

public class TokenAndPosition {
  // this class does what it says
  // it stores a token and the position of said token
  // why have this class at all?
  // when you're looping through a long list of things it's inefficient
  // to find the token that I want and then loop though again to find the index that it's at
  public Token token;
  public int index;
  public TokenAndPosition(Token t, int i) {
    this.token = t;
    this.index = i;
  }
  @Override
  public String toString() {
    return String.format(
      "%sTokenAndPosition %s{ %stoken%s: %s, %sindex%s: %s }", 
      //1           2   3     4   5   6     7   8

      Colors.yellow,   // 1
      Colors.reset,    // 2
      Colors.red,      // 3
      Colors.reset,    // 4
      (
        Colors.green
        + this.token.toString()
        + Colors.reset
      ),               // 5
      Colors.red,      // 6
      Colors.reset,    // 7
      (
        Colors.green
        + this.index
        + Colors.reset
      )                // 8
    );
    // okay so this one is crazy
    // it looks like this
    // TokenAndPosition { token: Token { value: +, type: TokenType { name: +/-, prec: 2 } }, index: 4 }
  }
}
