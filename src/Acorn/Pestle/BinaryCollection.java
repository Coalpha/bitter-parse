package Acorn.Pestle;

public class BinaryCollection {
  public TokenList left;
  public Token binop;
  public TokenList right;
  BinaryCollection(TokenList l, Token b, TokenList r) {
    this.left = l;
    this.binop = b;
    this.right = r;
  }
}
