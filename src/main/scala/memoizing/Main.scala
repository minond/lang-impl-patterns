package memoizing

object Main {
  def main(args: Array[String]) = {
    println(s"Input: ${args(0)}")

    val lexer = new LookaheadLexer(args(0))
    val parser = new BacktrackParser(lexer)

    parser.stat()
  }
}
