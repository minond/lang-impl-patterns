package backtrack

object Main {
  def main(args: Array[String]) = {
    val lexer = new LookaheadLexer(args(0))
    val parser = new BacktrackParser(lexer)

    parser.stat()
  }
}
