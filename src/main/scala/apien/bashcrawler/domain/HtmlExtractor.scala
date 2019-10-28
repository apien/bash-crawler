package apien.bashcrawler.domain
import apien.bashcrawler.domain.Message.{Content, Id, Points}
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import scala.jdk.CollectionConverters._

trait HtmlExtractor[T] {

  /**
	*  Parse raw html content to the [[Seq]] of the demanded objects. In case when object is unable to parse on the demanded object
	*  (lack of required field or unable to parse it) then omit it in the result list.
	  * @param html Html to parse on the [[Seq]] of [[T]].
	  * @return Parsed objects.
	  */
  def extractSeq(html: Html): Seq[T]
}

object HtmlExtractor {

  def extractSeq[T: HtmlExtractor](html: Html): Seq[T] = implicitly[HtmlExtractor[T]].extractSeq(html)

  implicit val messageExtractor = new HtmlExtractor[Message] {

    override def extractSeq(html: Html): Seq[Message] = {
      Jsoup
        .parse(html.value)
        .select(".post")
        .asScala
        .map { postElement =>
          val idOp = extractValue(postElement, ".qid")
            .flatMap(_.split("#").lastOption) //TODO convert it to pattern matching
            .flatMap(_.toLongOption)
            .map(Id)
          val pointsOp = extractValue(postElement, ".points")
            .flatMap(_.toLongOption)
            .map(Points)
          val contentOp = extractValue(postElement, ".post-body").map(Content)
          for {
            id <- idOp
            points <- pointsOp
            content <- contentOp
          } yield Message(id, points, content)
        }
        .toList
        .flatten
    }

    private def extractValue(element: Element, cssQuery: String): Option[String] =
      element
        .select(cssQuery)
        .asScala
        .headOption
        .map(_.text())
  }
}
