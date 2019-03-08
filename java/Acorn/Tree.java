package Acorn;
import VT100.Colors;
import Acorn.Pestle.*;
import Acorn.Stove.*;
import Acorn.Squirrel.*;

public class Tree {
  public static String grow(String inp) {
    TokenList tokens = new Tokenizer(inp).getTokens();
    Expression AST = new Parser(tokens).AST;
    // this should be a completed abstract syntax tree by the time it gets here
    Fraction res = Squirrel.feed(AST);
    // Squirrel.feed returns a Fraction
    return AST.toString() + Colors.purple + " = " + Colors.reset + res.simplify().toMixedNumber();
    // do not feed the animals
  }
  public static String roots(String inp) {
    TokenList tokens = new Tokenizer(inp).getTokens();
    Expression AST = new Parser(tokens).AST;
    // this should be a completed abstract syntax tree by the time it gets here
    Fraction res = Squirrel.feed(AST);
    // Squirrel.feed returns a Fraction
    return res.simplify().toMixedNumber();
    // do not feed the animals
  }
}
