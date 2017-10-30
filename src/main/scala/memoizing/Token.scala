package memoizing

case class Token(val tokenType: Int, text: String) {
  override def toString() =
    s"<${LookaheadLexer.tokenNames(tokenType)}, '$text'>"
}
