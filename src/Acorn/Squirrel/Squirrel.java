package Acorn.Squirrel;
import Acorn.Stove.*;
import Acorn.WTFerror;

public class Squirrel {
  public static Fraction feed(Expression expr) {
    String type = expr.getType();
    if (type.equals("BinopExpression")) {
      return Squirrel.feed((BinopExpression) expr);
    } else if (type.equals("Literal")) {
      return Squirrel.feed((Literal) expr);
    } else if (type.equals("ParenExpression")) {
      return Squirrel.feed((ParenExpression) expr);
    }
    throw new WTFerror();
  }
  public static Fraction feed(ParenExpression p) {
    // System.out.println(p);
    return Squirrel.feed(p.expr);
  };
  public static Fraction feed(Literal l) {
    // System.out.println(l);
    return Fraction.fromInteger(Integer.parseInt(l.val));
  };
  public static Fraction feed(BinopExpression expr) {
    // System.out.println(expr);
    Fraction left = Squirrel.feed(expr.left);
    Fraction right = Squirrel.feed(expr.right);
    if (expr.binop.equals("/")) {
      return left.multiply(right.reciprocal());
    } else if (expr.binop.equals("*")) {
      return left.multiply(right);
    } else if (expr.binop.equals("+")) {
      return left.add(right);
    } else if (expr.binop.equals("-")) {
      return left.add(right.multiply(-1));
    }
    throw new WTFerror();
  }
}
