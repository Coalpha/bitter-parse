package Acorn.Pestle;
import VT100.Colors;

public class TokenAndPosition {
  public Token token;
  public int index;
  TokenAndPosition(Token t, int i) {
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
  }
}
