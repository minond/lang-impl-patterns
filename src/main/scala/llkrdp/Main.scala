package llkrdp

object Main {
  def main(args: Array[String]) = {
    val lexer = new LookaheadLexer(args(0))
    val parser = new LookaheadParser(lexer, 2)

    parser.list()
  }
}
