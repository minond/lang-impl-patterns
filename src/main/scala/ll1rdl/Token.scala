package ll1rdl

case class Token(val tokenType: Int, text: String) {
  override def toString() =
    s"<${ListLexer.tokenNames(tokenType)}, '$text'>"
}
