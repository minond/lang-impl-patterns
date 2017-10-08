package ll1rdp

class ListParser(input: Lexer) extends Parser(input) {
  def list(): Unit = {
    matching(ListLexer.LBRACK)
    elements()
    matching(ListLexer.RBRACK)
  }

  def elements(): Unit = {
    element()

    while (lookahead.tokenType == ListLexer.COMMA) {
      matching(ListLexer.COMMA)
      element()
    }
  }

  def element(): Unit = {
    lookahead.tokenType match {
      case ListLexer.NAME =>
        matching(ListLexer.NAME)

      case ListLexer.LBRACK =>
        list()

      case _ =>
        throw new Error(s"Expecting list but found $lookahead")
    }
  }
}
