package llkrdp

abstract class Parser(input: Lexer, k: Int) {
  var lookahead = new Array[Token](k)
  var p = 0

  (1 to k) foreach { _ => consume() }

  def wrapped(i: Int) =
    i % k

  def consume() = {
    lookahead(p) = input.nextToken()
    p = wrapped(p + 1)
  }

  def LT(i: Int): Token =
    lookahead(wrapped(p + i - 1))

  def LA(i: Int): Int =
    LT(i).tokenType

  def matching(expecting: Int) = {
    if (LA(1) == expecting) {
      consume()
    } else {
      throw new Error(s"Expecting ${input.getTokenName(expecting)} but found ${LT(1)}")
    }
  }
}
