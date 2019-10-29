package apien.bashcrawler.infrastructure
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import apien.bashcrawler.domain.Message
import apien.bashcrawler.domain.Message.{Content, Id, Points}
import spray.json.{DefaultJsonProtocol, JsNumber, JsObject, JsString, JsonWriter}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit protected val idWriter: JsonWriter[Id] = (id: Id) => JsNumber(id.value)
  implicit protected val pointsWriter: JsonWriter[Points] = (points: Points) => JsNumber(points.value)
  implicit protected val contentWriter: JsonWriter[Content] = (content: Content) => JsString(content.value)
  implicit protected val messageWriter: JsonWriter[Message] = (message: Message) =>
    JsObject(
      "id" -> idWriter.write(message.id),
      "points" -> pointsWriter.write(message.points),
      "content" -> contentWriter.write(message.content)
  )
}
