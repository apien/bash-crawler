package apien.bashcrawler.test
import apien.bashcrawler.domain.Message.{Content, Id, Points}

trait BaseSpec {

  implicit def int2Id(value: Long): Id = Id(value)

  implicit def int2Points(value: Long): Points = Points(value)

  implicit def string2Content(value: String): Content = Content(value)
}
