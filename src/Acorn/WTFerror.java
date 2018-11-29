package Acorn;
import java.lang.RuntimeException;

public class WTFerror extends RuntimeException {
  static final long serialVersionUID = 0xdead;
  public WTFerror() {
    super();
  }
  public WTFerror(String e) {
    super(e);
  }
}
