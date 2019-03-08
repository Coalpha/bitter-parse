package Acorn.Pestle;
import java.lang.RuntimeException;

public class UnexpectedCharacter extends RuntimeException {
  static final long serialVersionUID = 123;
  public UnexpectedCharacter(char c) {
    super("Unexpected Character: \"" + c + "\"!");
  }
}
