package apien.bashcrawler.infrastructure
import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.Materializer
import akka.stream.scaladsl.{RestartSource, Sink, Source}
import apien.bashcrawler.domain.BaschClient.FailedFetchMessagesException
import apien.bashcrawler.domain._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.control.NonFatal

//TODO user refined or other type not just plain String for url

/**
  *  Implementation of [[BaschClient]] based on scrapping. We parse html content of the page.
  * @param baseBashUrl Base url to bash, without '/' at the end.
  * @param httpClient Http client to make a http request.
  * @param actorSystem Actor system
  * @param materializer Materializer.
  * @param messageParser Parser to extract [[Message]] from a html content.
  */
class ScrapperBaschClient(baseBashUrl: String, httpClient: HttpRequest => Future[HttpResponse])(
    implicit actorSystem: ActorSystem,
    materializer: Materializer,
    messageParser: HtmlExtractor[Message]
) extends BaschClient {

  override def getMessages(pageNumber: PageNumber): Future[List[Message]] = {
    fetchPage(pageNumber).map { pageHtml =>
      messageParser.extractSeq(pageHtml).toList
    }
  }

  private def fetchPage(pageNumber: PageNumber): Future[Html] = {
    RestartSource
      .withBackoff(
        minBackoff = 100.milliseconds,
        maxBackoff = 10.seconds,
        randomFactor = 0.05,
        maxRestarts = 2
      ) { () =>
        val responseFuture: Future[HttpResponse] = httpClient(HttpRequest(uri = urlToPage(pageNumber)))
        Source
          .fromFuture(responseFuture)
          .mapAsync(parallelism = 1) {
            case HttpResponse(StatusCodes.OK, _, entity, _) =>
              Unmarshaller
                .stringUnmarshaller(entity)
                .map(Html)
            case HttpResponse(statusCode, _, _, _) =>
              throw new RuntimeException(s"Unable to fetch given page '$pageNumber' with status '$statusCode'")
          }
      }
      .runWith(Sink.head)
      .recover {
        case NonFatal(exception: Exception) =>
          throw FailedFetchMessagesException(pageNumber, exception.getMessage)
      }
  }

  /**
	  * @param number Number of the demanded page.
	  * @return Ready to use the uri to fetch a given page.
	  */
  private def urlToPage(number: PageNumber): Uri = s"$baseBashUrl/latest/?page=${number.value}"
}
