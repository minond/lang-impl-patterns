package memoizing

import scala.collection.mutable.Map

object Parser {
  val FAILED = -1
}

abstract class Parser(input: Lexer) {
  type Memo = Map[Int, Int]

  var markers = List[Int]()
  var lookahead = List[Token]()

  var p = 0

  sync(1)

  def clearMemo(): Unit

  def memoize(memo: Memo, index: Int, failed: Boolean): Memo = {
    val value = if (failed) Parser.FAILED else p
    memo + (index -> value)
  }

  def alreadyParsedRule(memo: Memo): Boolean = {
    val ret = memo.getOrElse(p, return false)

    println(s"Memoized computation at $p; Seeking to $ret (${lookahead(ret).text})")

    if (ret == Parser.FAILED) {
      throw PreviousParseFailedException(s"At index $p")
    }

    seek(ret)
    return true
  }


  def consume() = {
    p = p + 1

    if (p == lookahead.size && !isSpeculating()) {
      p = 0
      lookahead = List[Token]()
      clearMemo()
    }

    sync(1)
  }

  def mark() = {
    markers = markers :+ p
    p
  }

  def release() = {
    val marker = markers.last
    markers = markers.init
    seek(marker)
  }

  def seek(i: Int) = {
    p = i
  }

  def matching(expecting: Int) = {
    if (LA(1) == expecting) {
      consume()
    } else {
      throw MismatchedTokenException(s"Expecting ${input.getTokenName(expecting)} but found ${LT(1)}")
    }
  }

  def sync(i: Int) = {
    if (p + i - 1 > lookahead.size - 1) {
      val n = (p + i - 1) - (lookahead.size - 1)
      fill(n)
    }
  }

  def fill(i: Int) = {
    (1 to i) foreach {
      _ => lookahead = lookahead :+ input.nextToken
    }
  }

  def LT(i: Int) = {
    sync(i)
    lookahead(p + i - 1)
  }

  def LA(i: Int) = {
    LT(i).tokenType
  }

  def isSpeculating() = {
    markers.size > 0
  }
}
