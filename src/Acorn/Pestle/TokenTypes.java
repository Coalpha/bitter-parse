package Acorn.Pestle;

class Binop extends TokenType {
  Binop(String name, int prec) {
    super(name, 2, prec);
  }
}
public class TokenTypes {
  static TokenType sof = new TokenType("sof");
  static TokenType eof = new TokenType("eof");
  static TokenType parenR = new TokenType("prR");
  static TokenType num = new TokenType("num", 4);
  static TokenType parenL = new TokenType("prL", 6);
  static TokenType plusMin = new TokenType("+/-", 7, 1);

  static Binop star = new Binop("str", 2);
  static Binop slash = new Binop("sls", 5); // This should be 2, but if it is 2 then the parser parses it in a way that is technically correct but super wierd
  static Binop carrot = new Binop("crt", 3);
  static Binop underscore = new Binop("uds", 4); // This is literally just a plus with higher precedence, but has to be less than a slash because if it is not, then you are adding to the numerator of the fraction, not the whole fraction
}
