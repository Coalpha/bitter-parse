package Acorn.Stove;
import VT100.Colors;

public class BinopExpression extends Expression {
  public final String type = "BinopExpression";
  public Expression left;
  public String binop;
  public Expression right;
  BinopExpression(Expression left, String binop, Expression right) {
    this.left = left;
    this.binop = binop;
    this.right = right;
  }
  @Override
  public String getType() {
    return this.type;
  }
  @Override
  public String toString() {
    return (
      Colors.blue + "(" + Colors.reset
      + this.left.toString() + " " + Colors.red
      + this.binop + Colors.reset + " " + this.right.toString()
      + Colors.blue + ")" + Colors.reset
    );
    // looks like this:
    // ( 3 + 2 )
  }
}
