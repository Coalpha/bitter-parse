package Acorn.Pestle;

public class BinopCollection {
  // this class is returned by Acorn.Pestle.TokenList.binopSplit
  // this class allows some of the splitting logic to be moved
  // from Acorn.Stove into Acorn.Pestle.TokenList
  public TokenList left;
  public Token binop;
  public TokenList right;
  // here's why this class is used. Take this expression for instance:
  // [1 * 2] + [3]

  // can be converted into this V

  // left = [1 * 2]
  // binop = +
  // right = [3]
  BinopCollection(TokenList l, Token b, TokenList r) {
    this.left = l;
    this.binop = b;
    this.right = r;
  }
}
