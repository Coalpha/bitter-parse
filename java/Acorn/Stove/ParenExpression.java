package Acorn.Stove;
import VT100.Colors;

public class ParenExpression extends Expression {
  public Expression expr;
  public final String type = "ParenExpression";
  ParenExpression(Expression expr) {
    this.expr = expr;
  }
  @Override
  public String getType() {
    return this.type;
  }
  @Override
  public String toString() {
    return (
      Colors.green + "("
      + Colors.reset + this.expr.toString()
      + Colors.green + ")" + Colors.reset
    );
  }
}
