package Acorn.Pestle;
import VT100.Colors;

public class TokenType {
  final String name;
  public boolean binop = false;
  public final int prec;
  TokenType(String name, int prec, boolean binop) {
    this.name = name;
    this.prec = prec;
    this.binop = true;
  }
  public TokenType(String name, int prec) {
    this.name = name;
    this.prec = prec;
  }
  TokenType(String name) {
    this.name = name;
    this.prec = 0;
  }
  @Override
  public String toString() {
    return String.format(
      "%sTokenType %s{ %sname%s: %s, %sprec%s: %s }", 
      //1           2   3     4   5   6     7   8

      Colors.yellow,     // 1
      Colors.reset,    // 2
      Colors.red,      // 3
      Colors.reset,    // 4
      (
        Colors.green
        + this.name
        + Colors.reset
      ),               // 5
      Colors.red,      // 6
      Colors.reset,    // 7
      (
        Colors.green
        + this.prec
        + Colors.reset
      )                // 8
    );
  }
}
