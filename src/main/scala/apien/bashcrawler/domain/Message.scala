package apien.bashcrawler.domain
import apien.bashcrawler.domain.Message.{Content, Id, Points}

/**
  * It represents a single bash post.
  *
  * @param id Identifier of the message.
  * @param points Amount of collected points.
  * @param content content of the message.
  */
case class Message(id: Id, points: Points, content: Content)

object Message{

	case class Id(value: Long) extends AnyVal

	case class Points(value: Long) extends AnyVal

	case class Content(value: String) extends AnyVal
}
