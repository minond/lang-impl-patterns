package memoizing

import scala.collection.mutable.Map
import scala.util.{Failure, Success, Try}

class BacktrackParser(input: Lexer) extends Parser(input) {
  var listMemo: Memo = Map[Int, Int]()
  var assignMemo: Memo = Map[Int, Int]()

  def clearMemo(): Unit = {
    listMemo = Map[Int, Int]()
    assignMemo = Map[Int, Int]()
  }

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
      case Success(_) =>
        println("Speculation was correct")
        true

      case Failure(_) =>
        println("Speculation was incorrect")
        false
    }
  }

  def speculateStatAlt1() = {
    speculate(() => Try {
      println("Speculating `list`")
      list()
      matching(Lexer.EOF_TYPE)
    })
  }

  def speculateStatAlt2() = {
    speculate(() => Try {
      println("Speculating `assign`")
      assign()
      matching(Lexer.EOF_TYPE)
    })
  }

  def _assign(): Unit = {
    println(s"Parsing `assign` at $p")
    list()
    matching(LookaheadLexer.EQUALS)
    list()
  }

  def assign(): Unit = {
    var failed = false
    var startIndex = p

    if (isSpeculating() && alreadyParsedRule(assignMemo)) {
      return
    }

    try {
      _assign()
    } catch {
      case err: RecognitionException =>
        failed = true
        throw err

      case err: Throwable =>
        failed = true
        throw err
    } finally {
      if (isSpeculating()) {
        println(s"Storing result of `assign` memoization. Start: $startIndex, end: $p, failed: $failed")
        assignMemo = memoize(assignMemo, startIndex, failed)
      } else {
        println("Not speculating so not storing `list` memoize.")
      }
    }
  }

  def _list(): Unit = {
    println(s"Parsing `list` at $p")
    matching(LookaheadLexer.LBRACK)
    elements()
    matching(LookaheadLexer.RBRACK)
  }

  def list(): Unit = {
    var failed = false
    var startIndex = p

    if (isSpeculating() && alreadyParsedRule(listMemo)) {
      return
    }

    try {
      _list()
    } catch {
      case err: RecognitionException =>
        failed = true
        throw err

      case err: Throwable =>
        failed = true
        throw err
    } finally {
      if (isSpeculating()) {
        println(s"Storing result of `list` memoization. Start: $startIndex, end: $p, failed: $failed")
        listMemo = memoize(listMemo, startIndex, failed)
      } else {
        println("Not speculating so not storing `list` memoize.")
      }
    }
  }

  def elements(): Unit = {
    println(s"Parsing `elements` at $p")
    element()

    while (LA(1) == LookaheadLexer.COMMA) {
      matching(LookaheadLexer.COMMA)
      element()
    }
  }

  def element() = {
    println(s"Parsing `element` at $p")

    (LA(1), LA(2)) match {
      case (LookaheadLexer.NAME, LookaheadLexer.EQUALS) =>
        matching(LookaheadLexer.NAME)
        matching(LookaheadLexer.EQUALS)
        matching(LookaheadLexer.NAME)

      case (LookaheadLexer.NAME, _) =>
        matching(LookaheadLexer.NAME)

      case (LookaheadLexer.LBRACK, _) =>
        list()

      case (LookaheadLexer.RBRACK, _) =>
        println("Found empty list.")
        throw new Error(s"Expecting name of list but found ${LT(1)}")

      case _ =>
        throw new Error(s"Expecting name of list but found ${LT(1)}")
    }
  }
}
