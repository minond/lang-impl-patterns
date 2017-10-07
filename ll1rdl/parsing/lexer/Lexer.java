public abstract class Lexer {
  public static final char EOF = (char) -1;
  public static final int EOF_TYPE = 1;

  String input; // the input string we're parsing
  int p;        // index into input of current char
  char c;       // current char

  public abstract Token nextToken();
  public abstract String getTokenName(int type);

  public Lexer(String input) {
    this.input = input;
    p = 0;
    c = input.charAt(p);
  }

  public void consume() {
    p++;

    if (p >= input.length()) {
      c = EOF;
    } else {
      c = input.charAt(p);
    }
  }

  public void match(char x) {
    if (c == x) {
      consume();
    } else {
      throw new Error("Expecting " + x + " but found " + c + ".");
    }
  }
}
