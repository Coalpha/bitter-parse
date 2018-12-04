package Acorn;
import java.lang.RuntimeException;

public class AcornError extends RuntimeException {
  static final long serialVersionUID = 0xdead;
  public AcornError() {
    super();
  }
  public AcornError(String e) {
    super(e);
  }
}
