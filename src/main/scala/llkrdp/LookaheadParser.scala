package llkrdp

class LookaheadParser(input: Lexer, k: Int) extends Parser(input, k) {
  def list(): Unit = {
    matching(LookaheadLexer.LBRACK)
    elements()
    matching(LookaheadLexer.RBRACK)
  }

  def elements(): Unit = {
    element()

    while (LA(1) == LookaheadLexer.COMMA) {
      matching(LookaheadLexer.COMMA)
      element()
    }
  }

  def element() = {
    (LA(1), LA(2)) match {
      case (LookaheadLexer.NAME, LookaheadLexer.EQUALS) =>
        matching(LookaheadLexer.NAME)
        matching(LookaheadLexer.EQUALS)
        matching(LookaheadLexer.NAME)

      case (LookaheadLexer.NAME, _) =>
        matching(LookaheadLexer.NAME)

      case (LookaheadLexer.LBRACK, _) =>
        list()

      case _ =>
        throw new Error(s"Expecting name of list but found ${LT(1)}")
    }
  }
}
