package Acorn;
import VT100.Colors;
import Acorn.Pestle.*;
import Acorn.Stove.*;
import Acorn.Squirrel.*;

public class Tree {
  public static String grow(String inp) {
    TokenList tokens = new Tokenizer(inp).getTokens();
    Expression AST = new Parser(tokens).AST;
    Fraction res = Squirrel.feed(AST);
    return AST.toString() + Colors.purple + " = " + Colors.reset + res.simplify().toMixedNumber();
    // do not feed the animals
  }
}
