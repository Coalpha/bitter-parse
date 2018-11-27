package Acorn.Pestle;

public class TokenType {
  final String name;
  public final int prec;
  TokenType(String name, int prec) {
    this.name = name;
    this.prec = prec;
  }
  TokenType(String name) {
    this.name = name;
    this.prec = 0;
  }
  @Override
  public String toString() {
    return this.name + "@" + this.prec;
  }
}
