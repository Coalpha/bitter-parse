import Acorn.Tree;
import java.util.function.Function;

public class FracCalc {
  static Function<String, String> intake = (String inp) -> {
    return Tree.grow(inp);
  };
  public static void main(String[] args) {
    new Console(intake);
  }
}
