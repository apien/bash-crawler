package apien.bashcrawler.domain
import scala.concurrent.Future

trait BatchClient {

  /**
	* Fetch given page from the bash.
	 * @param number Demanded page.
	 * @return Future with result.
	 *
	 */
  def getMessages(number: PageNumber): Future[List[Message]]
}

object BatchClient {

  case class FailedFetchMessagesException(pageNumber: PageNumber, message: String) extends RuntimeException
}
