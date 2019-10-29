package apien.bashcrawler.domain
import apien.bashcrawler.domain.BaschClient.FailedFetchMessagesException

import scala.concurrent.Future

trait BaschClient {

  /**
	* Fetch given page from the bash.
	 * @param number Demanded page.
	 * @return Future with result.
	 *
	 * @throws FailedFetchMessagesException Fetch demanded page failed.
	 */
  def getMessages(number: PageNumber): Future[List[Message]]
}

object BaschClient {

  case class FailedFetchMessagesException(pageNumber: PageNumber, message: String) extends RuntimeException(message)
}
