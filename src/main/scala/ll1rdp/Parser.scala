package ll1rdp

abstract class Parser(input: Lexer) {
  var lookahead = input.nextToken()

  def matching(expecting: Int) = {
    if (lookahead.tokenType == expecting) {
      consume()
    } else {
      throw new Error(s"Expecting ${input.getTokenName(expecting)} but found ${lookahead}.")
    }
  }

  def consume() = {
    lookahead = input.nextToken()
  }
}
