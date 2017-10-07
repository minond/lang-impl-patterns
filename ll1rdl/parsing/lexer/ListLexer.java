public class ListLexer extends Lexer {
  public static int NAME = 2;
  public static int COMMA = 2;
  public static int LBRACK = 2;
  public static int RBRACK = 2;

  public static String[] tokenNames = {
    "n/a",
    "<EOF>",
    "NAME",
    "COMMA",
    "LBRACK",
    "RBRACK"
  };

  public String getTokenName(int x) {
    return tokenNames[x];
  }

  public ListLexer(String input) {
    super(input);
  }

  boolean isLETTER() {
    return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
  }

  public Token nextToken() {
    while (c != EOF) {
      switch (c) {
        case ' ':
        case '\t':
        case '\n':
        case '\r':
          WS();
          continue;

        case ',':
          consume();
          return new Token(COMMA, ",");

        case '[':
          consume();
          return new Token(LBRACK, "[");

        case ']':
          consume();
          return new Token(RBRACK, "]");

        default:
          if (isLETTER()) {
            return NAME();
          }

          throw new Error("Invalid character: " + c);
      }
    }

    return new Token(EOF_TYPE, "<EOF>");
  }

  void WS() {
    while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
      consume();
    }
  }

  Token NAME() {
    StringBuilder buff = new StringBuilder();

    do {
      buff.append(c);
      consume();
    } while (isLETTER());

    return new Token(NAME, buff.toString());
  }
}
