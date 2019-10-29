package apien.bashcrawler.infrastructure
import java.nio.charset.Charset
import java.nio.file.{Files, Path}

import apien.bashcrawler.domain.MessageRepository.StoreMessagesFailedException
import apien.bashcrawler.domain.{Message, MessageRepository}
import spray.json.JsArray

import scala.util.control.NonFatal
import scala.util.{Try, Using}

/**
  * Implementation of the [[MessageRepository]] which persist messages in the local file.
  * @param resultFilePath Path to a file where it persists the data.
  */
class MessageFileRepository(resultFilePath: Path) extends MessageRepository with JsonSupport {

  override def store(messages: List[Message]): Try[Unit] = {
    val jsonString = messageToJson(messages)
    Using(Files.newBufferedWriter(resultFilePath, Charset.forName("UTF-8"))) { writer =>
      writer.write(jsonString)
    }.recover {
      case NonFatal(error) => Try(StoreMessagesFailedException(error))
    }
  }

  /**
	* Transforms list of [[Message]] into an array of json in string form.
	  * @param messages List of objects to convert.
	  * @return Json array as string.
	  */
  private def messageToJson(messages: List[Message]): String = {
    val messagesJson = messages.map(messageWriter.write)
    JsArray(messagesJson: _*).toString()
  }
}
