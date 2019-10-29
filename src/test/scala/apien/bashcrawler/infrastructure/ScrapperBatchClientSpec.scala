package apien.bashcrawler.infrastructure
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCodes}
import akka.stream.{ActorMaterializer, Materializer}
import akka.testkit.TestKit
import apien.bashcrawler.domain.{BatchClient, Message, PageNumber}
import apien.bashcrawler.test.{BaseSpec, TestDataSpec}
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Second, Span}
import org.scalatest.{FlatSpecLike, Matchers}

import scala.collection.mutable
import scala.concurrent.Future

class ScrapperBatchClientSpec
    extends TestKit(ActorSystem("MySpec"))
    with FlatSpecLike
    with Matchers
    with TestDataSpec
    with ScalaFutures
    with BaseSpec {

  private implicit val materializer: Materializer = ActorMaterializer()
  private val featureTimeout = Timeout.apply(Span(1, Second))

  "ScrapperBatchClient.getMessages" should "return fetched message" in {
    val response = HttpResponse()
      .withStatus(StatusCodes.OK)
      .withEntity(messageHtml.value)

    buildClient(response).getMessages(PageNumber(1)).futureValue should equal(
      List(Message(4862636L, -169, "It is content of the post."))
    )
  }

  it should "returns objects which can be parsed to the message and omit invalid one" in {
    val response = HttpResponse()
      .withStatus(StatusCodes.OK)
      .withEntity(messageHtml.value + messageHtmlWithoutId.value)

    buildClient(response).getMessages(PageNumber(1)).futureValue should equal(
      List(Message(4862636L, -169, "It is content of the post."))
    )
  }

  it should "make another attempt to fetch messages" in {
    val responseQueue = mutable.Queue(
      HttpResponse().withStatus(StatusCodes.NotFound),
      HttpResponse()
        .withStatus(StatusCodes.OK)
        .withEntity(messageHtml.value)
    )
    val client = buildClient((_: HttpRequest) => Future.successful(responseQueue.dequeue()))

    client.getMessages(PageNumber(1)).futureValue should equal(
      List(Message(4862636L, -169, "It is content of the post."))
    )
  }

  it should "thrown FailedFetchMessagesException when number of attempts has been exceed" in {
    val responseQueue = mutable.Queue(
      HttpResponse().withStatus(StatusCodes.NotFound),
      HttpResponse().withStatus(StatusCodes.NotFound)
    )
    val client = buildClient((_: HttpRequest) => Future.successful(responseQueue.dequeue()))

    whenReady(client.getMessages(PageNumber(1)).failed, featureTimeout) { error =>
      error shouldBe a[BatchClient.FailedFetchMessagesException]
    }
  }

  private def buildClient(response: HttpResponse): ScrapperBatchClient =
    buildClient((_: HttpRequest) => Future.successful(response))

  private def buildClient(httpClient: HttpRequest => Future[HttpResponse]): ScrapperBatchClient =
    new ScrapperBatchClient("http://bash.org.pl", httpClient)
}
