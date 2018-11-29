package Acorn.Pestle;
import VT100.Colors;

public class Token {
  public String value;
  public final TokenType type;
  public static Token NA = new Token("N/A", TokenTypes.NA);
  Token(String value, TokenType type) {
    this.value = value;
    this.type = type;
  }
  Token(char value, TokenType type) {
    this.value = Character.toString(value);
    this.type = type;
  }
  void append(char value) {
    this.value += Character.toString(value);
  }
  public int prec() {
    return this.type.prec;
  }
  @Override
  public String toString() {
    return String.format(
      "%sToken %s{ %svalue%s: %s, %stype%s: %s }", 
      //1           2   3     4   5   6     7   8

      Colors.yellow,     // 1
      Colors.reset,    // 2
      Colors.red,      // 3
      Colors.reset,    // 4
      (
        Colors.green
        + this.value
        + Colors.reset
      ),               // 5
      Colors.red,      // 6
      Colors.reset,    // 7
      (
        Colors.green
        + this.type.toString()
        + Colors.reset
      )                // 8
    );
  }
}
