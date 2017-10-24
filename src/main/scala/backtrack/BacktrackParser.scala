package backtrack

import scala.util.{Failure, Success, Try}

class BacktrackParser(input: Lexer) extends Parser(input) {
  def stat() = {
    if (speculateStatAlt1()) {
      list()
      matching(Lexer.EOF_TYPE)
    } else if (speculateStatAlt2()) {
      assign()
      matching(Lexer.EOF_TYPE)
    } else {
      throw NoViableAltException(s"Expecting stat but found ${LT(1)}.")
    }
  }

  def speculate(tester: () => Try[Unit]): Boolean = {
    mark()
    val test = tester()
    release()

    test match {
      case Success(_) => true
      case Failure(_) => false
    }
  }

  def speculateStatAlt1() = {
    speculate(() => Try {
      list()
      matching(Lexer.EOF_TYPE)
    })
  }

  def speculateStatAlt2() = {
    speculate(() => Try {
      assign()
      matching(Lexer.EOF_TYPE)
    })
  }

  def assign(): Unit = {
    list()
    matching(LookaheadLexer.EQUALS)
    list()
  }

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
