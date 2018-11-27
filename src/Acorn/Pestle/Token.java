package Acorn.Pestle;

public class Token {
  public String value;
  public final TokenType type;
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
    return this.value + ": " + this.type.toString();
  }
}
