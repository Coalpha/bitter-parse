import VT100.Colors;
import java.util.Scanner;
import java.util.function.Function;

class Console {
  Scanner s = new Scanner(System.in);
  Console(Function<String, String> callback) {
    while (true) {
      // it's actually okay that I never `break` this while loop
      System.out.print(Colors.grey + "$ " + Colors.reset);
      String inp = s.nextLine();
      if (inp.length() == 0) {
        // stop the user typing nothing
        System.out.println("You can't just type nothing...");
      }
      if (inp.matches("[qQeE]|quit|exit")) {
        // the system exits
        System.out.println(Colors.grey + "Shutdown gracefully" + Colors.reset);
        System.exit(0);
      }
      String res = callback.apply(inp);
      // the result of the callback
      System.out.println(res + "\n");
      // print the result of the callback with another newline
    }
  }
}
