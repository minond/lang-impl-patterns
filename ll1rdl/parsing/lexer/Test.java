// javac Test.java
// java Test '[a, b, c, d, e, f, g]'
public class Test {
  public static void main(String[] args) {
    ListLexer lexer = new ListLexer(args[0]);
    Token t = lexer.nextToken();

    while (t.type != Lexer.EOF_TYPE) {
      System.out.println(t);
      t = lexer.nextToken();
    }

    // eof
    System.out.println(t);
  }
}
