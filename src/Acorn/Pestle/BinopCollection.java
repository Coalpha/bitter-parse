package Acorn.Pestle;

public class BinopCollection {
  public TokenList left;
  public Token binop;
  public TokenList right;
  BinopCollection(TokenList l, Token b, TokenList r) {
    this.left = l;
    this.binop = b;
    this.right = r;
  }
}
