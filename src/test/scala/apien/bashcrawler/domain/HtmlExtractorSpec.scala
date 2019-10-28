package apien.bashcrawler.domain
import apien.bashcrawler.domain.HtmlExtractor.messageExtractor
import apien.bashcrawler.test.BaseSpec
import cats.syntax.option._
import org.scalatest.{FlatSpec, Matchers}

class HtmlExtractorSpec extends FlatSpec with Matchers with BaseSpec {

  "MessageHtmlExtractor.parse" should "return a parsed full object " in {
    messageExtractor.extractSeq(buildHtmlContent()) should equal(List(Message(123, 999, "Bash XYZ")))
  }

  it should "process other objects when one of them is invalid" in {
    val htmlContent = Html(buildHtmlContent(id = None) + buildHtmlContent().value)

    messageExtractor.extractSeq(htmlContent) should equal(List(Message(123, 999, "Bash XYZ")))
  }

  it should "return list of all extracted objects" in {
    val htmlContent = Html(
      buildHtmlContent().value +
        buildHtmlContent(id = "124".some, points = "546".some, message = "Message 2".some).value
    )

    messageExtractor.extractSeq(htmlContent) should equal(
      List(Message(123, 999, "Bash XYZ"), Message(124, 546, "Message 2"))
    )
  }

  it should "omit a object when it does not contain the id" in {
    messageExtractor.extractSeq(buildHtmlContent(id = None)) should equal(Nil)
  }

  it should "omit a object when the id can not be parsed to Long" in {
    messageExtractor.extractSeq(buildHtmlContent(id = "id not long".some)) should equal(Nil)
  }

  it should "omit a object when it does not contain the points" in {
    messageExtractor.extractSeq(buildHtmlContent(points = None)) should equal(Nil)
  }

  it should "omit a object when the points points can bot be parsed to Long" in {
    messageExtractor.extractSeq(buildHtmlContent(points = "points not long".some))
  }

  it should "omit a object when it does not contain a message content" in {
    messageExtractor.extractSeq(buildHtmlContent(message = None))
  }

  private def buildHtmlContent(
      id: Option[String] = "123".some,
      points: Option[String] = "999".some,
      message: Option[String] = "Bash XYZ".some
  ): Html = Html {
    """
		|<div id="d4863078" class="q post">
		|			<div class="bar">
		|				<div class="right">
		|					21 marca 2019 08:02
		|				</div>
		""".stripMargin +
      id.map(idValue => s"""<a class="qid click" href="/$idValue/">#$idValue</a>""".stripMargin).getOrElse("") +
      """"
		|				<a class="click votes rox" rel="nofollow" href="/rox/4863078/">+</a>
		""".stripMargin +
      points.map(pointsValue => s"""<span class=" points" style="visibility: hidden;">$pointsValue</span>""").getOrElse("") +
      """
		|				<a class="click votes sux" rel="nofollow" href="/sux/4863078/">-</a><a class="fbshare" href="http://www.facebook.com/sharer.php?u=http%3A%2F%2Fbash.org.pl%2F4863078%2F&amp;t=%0A%09%09%09%09%5Bmosfet%5D%20To%20ca%C5%82e%20przetwarzanie%20w%20chmurze%20jest%20super%2C%20ale%20szkoda%20mi%20mojego%20syna%0A%0A%5Bmosfet%5D%20bo%20nawet%20nie%20b%C4%99dzie%20wiedzia%C5%82%20co%20to%20jest%20fapfolder...%0A%09%09%09"></a>
		|				<span class="msg">&nbsp;</span>
		|			</div>
	""".stripMargin +
      message
        .map(messageValue => s"""<div class="quote post-content post-body">$messageValue	</div>""".stripMargin)
        .getOrElse("") +
      """</div>"""
  }
}
