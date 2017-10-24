package backtrack

class LookaheadLexer(input: String) extends Lexer(input) {
  override def getTokenName(tokenType: Int) =
    LookaheadLexer.tokenNames(tokenType)

  override def nextToken(): Token = {
    while (c != Lexer.EOF) {
      c match {
        case ' ' | '\t' | '\n' | '\r' =>
          Ws()

        case ',' =>
          consume()
          return Token(LookaheadLexer.COMMA, ",")

        case '[' =>
          consume()
          return Token(LookaheadLexer.LBRACK, "[")

        case ']' =>
          consume()
          return Token(LookaheadLexer.RBRACK, "]")

        case '=' =>
          consume()
          return Token(LookaheadLexer.EQUALS, "=")

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

    return Token(LookaheadLexer.NAME, buff.toString())
  }
}

object LookaheadLexer {
  val NAME = 2
  val COMMA = 3
  val LBRACK = 4
  val RBRACK = 5
  val EQUALS = 6

  val tokenNames = Array(
    "n/a",
    "<EOF>",
    "NAME",
    "COMMA",
    "LBRACK",
    "RBRACK",
    "EQUALS"
  )
}
