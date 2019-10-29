package apien.bashcrawler.domain

/**
  * It represents a page number for the bash. Minimal page is 1.
  * @param value -.
  */
case class PageNumber private (value: Long) extends AnyVal

object PageNumber {
  def apply(value: String): Option[PageNumber] = {
    value.toLongOption
      .filter(_ > 0)
      .map(value => new PageNumber(value))
  }
}
