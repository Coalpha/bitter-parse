import VT100.Colors;
import java.util.Scanner;
import java.util.function.Function;

class Console {
  Scanner s = new Scanner(System.in);
  Console(Function<String, String> callback) {
    System.out.println(Colors.green + "Type something in\n");
    while (true) {
      System.out.print(Colors.cyan + "$ " + Colors.reset);
      String inp = s.nextLine();
      if (inp.matches("[qQeE]|quit|exit")) {
        System.out.println("Shutdown gracefully");
        System.exit(0);
      }
      String res = callback.apply(inp);
      System.out.println(res + "\n");
    }
  }
}
