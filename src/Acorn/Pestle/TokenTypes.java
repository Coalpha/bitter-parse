package Acorn.Pestle;

public class TokenTypes {
  static TokenType sof = new TokenType("sof");
  static TokenType eof = new TokenType("eof");
  static TokenType num = new TokenType("num", 7);

  static TokenType parenL = new TokenType("prL", 6);
  static TokenType parenR = new TokenType("prR");
  
  static TokenType plusMin = new TokenType("+/-", 2);
  static TokenType star = new TokenType("str", 3);
  static TokenType slash = new TokenType("sls", 4);
  static TokenType underscore = new TokenType("uds", 5);
  // This is literally just a plus with higher precedence.
  // It has to be less than a slash otherwise it adds the whole number to the numerator of the fraction,
  // not the whole fraction
}
