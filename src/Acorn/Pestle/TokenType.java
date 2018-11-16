package Acorn.Pestle;

public class TokenType {
  public final int binop;
  final String label;
  final Boolean prefix;
  final Boolean beforeExpr;
  final Boolean startsExpr;
  private Boolean c(int i) {
    if (i == 0) {
      return false;
    }
    return true;
  }
  private int c(Boolean b) {
    if (b) {
      return 1;
    }
    return 0;
  }
  TokenType(String label, int field, int binop) {
    this.label = label;
    this.binop = binop;
    this.prefix = c(field
      & 0b001); // 1
    this.beforeExpr = c(field
      & 0b010); // 2
    this.startsExpr = c(field
      & 0b100); // 4
  }
  TokenType(String label, int field) {
    this.binop = 0;
    this.label = label;
    this.prefix = c(field & 0b001);
    this.beforeExpr = c(field & 0b010);
    this.startsExpr = c(field & 0b100);
  }
  TokenType(String label) {
    this.binop = 0;
    this.prefix = false;
    this.beforeExpr = false;
    this.startsExpr = false;
    this.label = label;
  }
  @Override
  public String toString() {
    return (
      this.label + " { binop: "
      + this.binop + ", prefix: "
      + c(this.prefix) + ", beforeExpr: "
      + c(this.beforeExpr) + ", startExpr: "
      + c(this.startsExpr) + " }"
    );
  }
}
