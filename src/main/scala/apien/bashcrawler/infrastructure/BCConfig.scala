package apien.bashcrawler.infrastructure
import java.nio.file.Path

import pureconfig._
import pureconfig.generic.auto._

/**
  * Bash crawler configuration.
  * @param resultFilePath Path of the file which contains the final result.
  * @param bashUrl Base url to bash, without "/" at the end.
  */
case class BCConfig(resultFilePath: Path, bashUrl: String)

object BCConfig {
  def loadConfig = ConfigSource.default.at("bashcrawler").loadOrThrow[BCConfig]
}
