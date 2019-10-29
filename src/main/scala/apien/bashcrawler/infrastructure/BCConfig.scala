package apien.bashcrawler.infrastructure
import java.nio.file.Path

import pureconfig._
import pureconfig.generic.auto._

/**
  * Bash crawler configuration.
  * @param resultFilePath Path of the file which contains the final result.
  */
case class BCConfig(resultFilePath: Path)

object BCConfig {
  def loadConfig = ConfigSource.default.at("bashcrawler").loadOrThrow[BCConfig]
}
