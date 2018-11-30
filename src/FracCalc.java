import Acorn.Tree;
import java.util.function.Function;

public class FracCalc {
  public static void main(String[] args) {
    new Console(FracCalc::produceAnswer);
  }
  public static String produceAnswer(String input) {
    return Tree.grow(input);
  }
  public static void tests() {
    
  }
}
