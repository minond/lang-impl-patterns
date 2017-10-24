package backtrack

case class RecognitionException(message: String)
  extends Exception(message)

case class NoViableAltException(message: String)
  extends Exception(message)

case class MismatchedTokenException(message: String)
  extends Exception(message)
