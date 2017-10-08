package ll1rdl

object Main {
  def main(args: Array[String]) = {
    val lexer = new ListLexer(args(0))
    var token: Token = null

    do {
      token = lexer.nextToken
      println(token)
    } while (token.tokenType != Lexer.EOF_TYPE)
  }
}
