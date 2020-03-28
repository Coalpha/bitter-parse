package Acorn.Stove;

public abstract class Expression extends Node {
  public String getType() {
    return this.type;
  };
  // seems silly right?
  // what if the input could be either a BinopExpression, a ParenExpression, or a Literal?
  // then the type is Expression and it needs to have a method to figure out which one of these to typecast to
}
