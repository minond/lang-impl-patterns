package ll1rdl

class ListLexer(input: String) extends Lexer(input) {
  override def getTokenName(tokenType: Int) =
    ListLexer.tokenNames(tokenType)

  override def nextToken(): Token = {
    while (c != Lexer.EOF) {
      c match {
        case ' ' | '\t' | '\n' | '\r' =>
          Ws()

        case ',' =>
          consume()
          return Token(ListLexer.COMMA, ",")

        case '[' =>
          consume()
          return Token(ListLexer.LBRACK, "[")

        case ']' =>
          consume()
          return Token(ListLexer.RBRACK, "]")

        case _ =>
          if (isLetter()) {
            return Name()
          } else {
            throw new Error(s"Invalid character: $c")
          }
      }
    }

    return Token(Lexer.EOF_TYPE, "<EOF>")
  }

  def isLetter() =
    c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'

  def Ws() = {
    while (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
      consume();
    }
  }

  def Name(): Token = {
    val buff = new StringBuilder

    do {
      buff.append(c)
      consume()
    } while (isLetter())

    return Token(ListLexer.NAME, buff.toString())
  }
}

object ListLexer {
  val NAME = 2
  val COMMA = 3
  val LBRACK = 4
  val RBRACK = 5

  val tokenNames = Array(
    "n/a",
    "<EOF>",
    "NAME",
    "COMMA",
    "LBRACK",
    "RBRACK"
  )
}
