package ll1rdp

object Main {
  def main(args: Array[String]) = {
    val lexer = new ListLexer(args(0))
    val parser = new ListParser(lexer)

    parser.list()
  }
}
