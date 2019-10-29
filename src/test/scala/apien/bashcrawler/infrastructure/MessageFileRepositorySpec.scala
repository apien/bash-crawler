package apien.bashcrawler.infrastructure
import java.nio.file.{Files, Path, Paths}

import apien.bashcrawler.test.TestDataSpec
import org.scalatest.{FlatSpec, Matchers}

class MessageFileRepositorySpec extends FlatSpec with Matchers with TestDataSpec {

  "MessageFileRepository.store" should "store messages to a file" in {
    val resultFilePath = createTempFile
    val repository = new MessageFileRepository(resultFilePath)
    val messages = List(
      buildMessage(),
      buildMessage(id = 28934L, points = 567L, content = "Other post")
    )

    repository.store(messages).get

    getFileContent(resultFilePath) should equal(
      """|[{"content":"Message Content 1","id":1,"points":11},{"content":"Other post","id":28934,"points":567}]""".stripMargin
    )
  }

  it should "create a file when it does not exist" in {
    val resultFile = createPathToTempFile
    val repository = new MessageFileRepository(resultFile)

    repository.store(List(buildMessage())).get

    getFileContent(resultFile) should equal(
      """|[{"content":"Message Content 1","id":1,"points":11}]""".stripMargin
    )
  }

  /**
	* Create a file and return it's path.
	* @return Path to created temporary file.
	*/
  private def createTempFile: Path = Files.createTempFile(System.currentTimeMillis().toString, ".json")
  //TODO remove temporary file after a test

  /**
	* Create to [[Path]] to temporary file.
	*
	* It only provides path to a temporary file but not yet created.
	* @return Path to a temporary file.
	*/
  private def createPathToTempFile: Path = {
    val temporaryDirPath = Files.createTempDirectory(s"bs_test_${System.currentTimeMillis()}")
    Paths.get(s"$temporaryDirPath/create_it.json")
  }

  /**
	* Get content of a given file.
	* @param path Path to file.
	* @return Content of the file.
	*/
  private def getFileContent(path: Path): String = io.Source.fromFile(path.toString).mkString

}
