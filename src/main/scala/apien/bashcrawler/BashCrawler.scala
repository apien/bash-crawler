package apien.bashcrawler
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.{ActorAttributes, ActorMaterializer, Supervision}
import apien.bashcrawler.BashCrawler.Statistics
import apien.bashcrawler.domain.{BatchClient, Message, MessageRepository, PageNumber}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BashCrawler(
    bashClient: BatchClient,
    messageRepository: MessageRepository
)(implicit val actorSystem: ActorSystem, materializer: ActorMaterializer)
    extends LazyLogging {

  /**
	  * It compose whole flow:
	  *   - load messages from the bash page
	  *	  - parse a received content into our domain objects
	  *	  - store it in repository
	  * @param pageNumber Last page to fetch the messages. Minimal value is 1.
	  * @return Result of operation.
	  *
	  * @throws Exception Store of messages failed.
	  */
  def process(pageNumber: PageNumber): Future[Statistics] = {
    val pageNumbers = for { number <- 1L to pageNumber.value } yield PageNumber(number)
    val messageFuture = Source(pageNumbers.toList)
      .mapAsync(2)(bashClient.getMessages)
      .withAttributes(ActorAttributes.supervisionStrategy(decider))
      .runWith(Sink.fold(List.empty[Message])(_ ++ _))

    for {
      messages <- messageFuture
      _ <- Future.fromTry(messageRepository.store(messages))
    } yield Statistics(messages.size)
  }

  private val decider: Supervision.Decider = {
    case _: BatchClient.FailedFetchMessagesException => Supervision.Resume
    case _                                           => Supervision.Stop
  }
}
object BashCrawler {
  case class Statistics(fetchedObjects: Long)
}
