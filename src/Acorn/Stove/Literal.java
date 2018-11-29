package Acorn.Stove;
import VT100.Colors;

public class Literal extends Expression {
  public String val;
  public final String type = "Literal";
  Literal(String value) {
    this.val = value;
  }
  @Override
  public String getType() {
    return this.type;
  }
  @Override
  public String toString() {
    return String.format(
      "%sLiteral%s { %s }",
      Colors.orange,
      Colors.reset,
      (Colors.cyan + this.val + Colors.reset)
    );
  }
}
