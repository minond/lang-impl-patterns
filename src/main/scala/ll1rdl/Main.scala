package ll1rdl

object Main {
  def main(args: Array[String]) {
    val lex = new ListLexer(args(0))
    var tok: Token = null

    do {
      tok = lex.nextToken
      println(tok)
    } while (tok.tokenType != Lexer.EOF_TYPE)
  }
}
