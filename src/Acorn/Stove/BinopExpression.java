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
      Colors.purple + "(" + Colors.reset
      + this.left.toString() + " " + Colors.yellow
      + this.binop + Colors.reset + " " + this.right.toString()
      + Colors.purple + ")" + Colors.reset
    );
  }
}
