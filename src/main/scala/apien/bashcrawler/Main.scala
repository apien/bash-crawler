package apien.bashcrawler

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import apien.bashcrawler.domain.PageNumber
import apien.bashcrawler.infrastructure.{BCConfig, JsonSupport, MessageFileRepository, ScrapperBaschClient}
import apien.bashcrawler.service.BashCrawler
import apien.bashcrawler.service.BashCrawler.Statistics
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main extends App with JsonSupport with LazyLogging {

  val pageNumber = if (args.length < 1) {
    println("Run crawler with one argument - how many first pages you want to fetch. Value not less than 1")
    None
  } else PageNumber(args(0))

  pageNumber.foreach(run)

  private def run(pageNumber: PageNumber): Unit = {
    implicit val system: ActorSystem = ActorSystem("bash-crawler")
    implicit val materializer: ActorMaterializer = ActorMaterializer()(system)
    val config = BCConfig.loadConfig
    val httpClient = (req: HttpRequest) => Http().singleRequest(req)
    val client = new ScrapperBaschClient(config.bashUrl, httpClient)
    val repository = new MessageFileRepository(config.resultFilePath)
    val crawler = new BashCrawler(client, repository)

    crawler
      .process(pageNumber)
      .onComplete {
        case Success(Statistics(fetchedObjects)) =>
          println("#########################################################")
          println(s"Successfully fetched $fetchedObjects messages from bash")
          println("#########################################################")
          cleanResources()
        case Failure(exception) =>
          logger.error(s"Ups! Something gone wrong $exception")
          cleanResources()
      }

    def cleanResources() = {
      materializer.shutdown()
      system.terminate()
    }
  }

}
