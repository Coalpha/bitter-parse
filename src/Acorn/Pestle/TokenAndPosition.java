package Acorn.Pestle;

public class TokenAndPosition {
  public Token token;
  public int index;
  TokenAndPosition(Token t, int i) {
    this.token = t;
    this.index = i;
  }
  @Override
  public String toString() {
    return "{" + this.token.toString() + "} [" + this.index + "]";
  }
}
