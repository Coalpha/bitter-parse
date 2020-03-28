// package com.gradescope.fraccalc;
import Acorn.Tree;
import java.util.function.Function;

public class FracCalc {
  public static void main(String[] args) {
    new Console(Tree::grow);
    // the console constructor takes a lambda
  }
  public static String produceAnswer(String input) {
    return Tree.roots(input);
  }
}
