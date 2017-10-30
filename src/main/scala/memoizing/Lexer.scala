package memoizing

abstract class Lexer(input: String) {
  var p = 0
  var c = input.charAt(0)

  def nextToken(): Token
  def getTokenName(t: Int): String

  def consume() = {
    p = p + 1

    if (p >= input.length()) {
      c = Lexer.EOF
    } else {
      c = input.charAt(p)
    }
  }
}

object Lexer {
  val EOF = -1.toChar
  val EOF_TYPE = 1
}
