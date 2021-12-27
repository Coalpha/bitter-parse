package Acorn.Squirrel;

public class Fraction {
  public static long gcm(int a, int b) {
    // https://stackoverflow.com/questions/6618994/simplifying-fractions-in-java
    return b == 0 ? a : Fraction.gcm(b, a % b);
  }
  static Fraction fromInteger(int i) {
    return new Fraction(i, 1);
  }
  int numerator;
  int denominator;
  Fraction(int nu, int de) {
    if (de == 0) {
      throw new DivideByZeroError();
    }
    if (de < 0) {
      this.numerator = nu * -1;
      this.denominator = de * -1;
    } else {
      this.numerator = nu;
      this.denominator = de;
    }
  }
  Fraction add(int i) {
    int nu = (i * this.denominator) + this.numerator;
    return new Fraction(nu, this.denominator);
  }
  Fraction add(Fraction f) {
    int nu = (this.numerator * f.denominator) + (f.numerator * this.denominator);
    int de = this.denominator * f.denominator;
    return new Fraction(nu, de);
  }
  Fraction multiply(int i) {
    return new Fraction(this.numerator * i, this.denominator);
  }
  Fraction multiply(Fraction f) {
    return new Fraction(this.numerator * f.numerator, this.denominator * f.denominator);
  }
  Fraction reciprocal() {
    return new Fraction(this.denominator, this.numerator);
  }
  public Fraction simplify() {
    int gcm = (int) Fraction.gcm(this.numerator, this.denominator);
    return new Fraction(this.numerator / gcm, this.denominator / gcm);
  }
  public String toMixedNumber() {
    if (this.denominator == 1) {
      // if it's a fraction like 5/1, just return 5
      return Integer.toString(this.numerator);
    }
    if (Math.abs(this.numerator) > this.denominator) {
      // for stuff like -10 / 2
      String whole = Integer.toString((int) Math.floor(this.numerator / this.denominator));
      String frac = new Fraction(
        Math.abs(this.numerator % this.denominator),
        this.denominator
      ).toString();
      if (!(frac.equals(""))) {
        whole += "_" + frac;
      }
      return whole;
    }
    return this.toString();
  }
  @Override
  public String toString() {
    if (this.numerator == 0) {
      return "";
    }
    return Integer.toString(this.numerator) + "/" + Integer.toString(this.denominator);
  }
}
