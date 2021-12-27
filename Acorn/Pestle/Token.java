package Acorn.Pestle;
import VT100.Colors;

public class Token {
  // a token is just a little bit of information
  // and by bit, I don't mean a literal bit of information
  public static Token NA = new Token("N/A", TokenTypes.NA);
  // this is a static property for methods / functions that want to initialize
  // a variable but don't know what token to use.
  public String value;
  public final TokenType type;
  // these two properties might look like:
  // value = "+"
  // type = TokenType.plusMin
  public Token(String value, TokenType type) {
    // why is this constructor public and the other one isn't?
    // it's actually accessed by Acorn.Stove which isn't within this package.
    this.value = value;
    this.type = type;
  }
  Token(char value, TokenType type) {
    this.value = Character.toString(value);
    this.type = type;
  }
  void append(char value) {
    // let's say that the token as it stands has a value of "12"
    // if the tokenizer runs into another number, it needs to add it onto this token
    this.value += Character.toString(value);
  }
  public int prec() {
    // just a faster way to get the operator precedence from the TokenType that's stored within
    return this.type.prec;
  }
  @Override
  public String toString() {
    return String.format(
      "%sToken %s{ %svalue%s: %s, %stype%s: %s }",
      //1           2   3     4   5   6     7   8

      Colors.yellow,   // 1
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
  // okay yeah, that's evil.
  // it looks like this:
  // Token { value: +, type: TokenType { name: +/-, prec: 2 } }
}
