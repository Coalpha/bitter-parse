package Acorn.Pestle;
import java.lang.RuntimeException;

public class UnexpectedToken extends RuntimeException {
  static final long serialVersionUID = 1337;
  // I've got no idea why the compiler wanted me to do this
  public UnexpectedToken(String token) {
    super("Unexpected Token: \"" + token + "\"!");
  }
  public UnexpectedToken(String token, String expected) {
    super("Unexpected Token: \"" + token + "\"! Expected \"" + expected + "\"!");
  }
}
