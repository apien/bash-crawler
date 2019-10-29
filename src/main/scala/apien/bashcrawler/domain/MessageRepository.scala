package apien.bashcrawler.domain
import scala.util.Try

/**
  * Repository for operations on our [[Message]].
  */
trait MessageRepository {

  /**
	*	Persist all messages.
	  * @param messages List of [[Message]] to persist.
	  */
  def store(messages: List[Message]): Try[Unit]
}

object MessageRepository {

  case class StoreMessagesFailedException(originalError: Throwable) extends RuntimeException(originalError)
}
