package Acorn.Pestle;

public class TokenTypes {
  public static TokenType sof = new TokenType("sof");
  public static TokenType eof = new TokenType("eof");
  public static TokenType space = new TokenType("wsp");
  
  public static TokenType parenL = new TokenType("prL", 6);
  public static TokenType parenR = new TokenType("prR");
  
  public static TokenType num = new TokenType("num", 7);
  
  public static TokenType plusMin = new TokenType("+/-", 2, true);
  public static TokenType star = new TokenType("str", 3, true);
  public static TokenType slash = new TokenType("sls", 5, true);
  public static TokenType underscore = new TokenType("uds", 4);
  // This is literally just a plus with higher precedence.
  // It has to be less than a slash otherwise it adds the whole number to the numerator of the fraction,
  // not the whole fraction
  public static TokenType NA = new TokenType("dflt");
}
