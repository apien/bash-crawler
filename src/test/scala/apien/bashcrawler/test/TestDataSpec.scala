package apien.bashcrawler.test
import apien.bashcrawler.domain.Html

trait TestDataSpec {
	val messageHtml = Html(
	  """
		  |<div id="content" class="">
		  |		<div id="d4862636" class="q post">
		  |			<div class="bar">
		  |				<div class="right">
		  |					28 marca 2017 20:02
		  |				</div>
		  |				<a class="qid click" href="/4862636/">#4862636</a>
		  |				<a class="click votes rox" rel="nofollow" href="/rox/4862636/">+</a>
		  |				<span class=" points" style="visibility: hidden;">-169</span>
		  |				<a class="click votes sux" rel="nofollow" href="/sux/4862636/">-</a><a class="fbshare" href="http://www.facebook.com/sharer.php?u=http%3A%2F%2Fbash.org.pl%2F4862636%2F&amp;t=%0A%09%09%09%09Backstory%3A%20Zapomnia%C5%82em%20odpowiedzi%20na%20pytania%20zabezpieczaj%C4%85ce%20potrzebne%20do%20zarz%C4%85dzania%20kontem%20Apple%0A%0A%0A%0A%3Ckazi%3E%20jaka%20mo%C5%BCe%20by%C4%87%20moja%20wymarzona%20praca%3F%0A%0A%3Cziomek%3E%20wpisz%20%C5%BCe%20zawsze%20chcia%C5%82e%C5%9B%20by%C4%87%20operatorem%20maszynki%20do%20mi%C4%99sa%0A%09%09%09"></a>
		  |				<span class="msg">&nbsp;</span>
		  |			</div>
		  |			<div class="quote post-content post-body">
		  |				It is content of the post.
		  |			</div>
		  |		</div>
		  |""".stripMargin
	)

  val messageHtmlWithoutId = Html(
	  """
		|<div id="content" class="">
		|		<div id="d4862636" class="q post">
		|			<div class="bar">
		|				<div class="right">
		|					28 marca 2017 20:02
		|				</div>
		|				<a class="click votes rox" rel="nofollow" href="/rox/4862636/">+</a>
		|				<span class=" points" style="visibility: hidden;">-169</span>
		|				<a class="click votes sux" rel="nofollow" href="/sux/4862636/">-</a><a class="fbshare" href="http://www.facebook.com/sharer.php?u=http%3A%2F%2Fbash.org.pl%2F4862636%2F&amp;t=%0A%09%09%09%09Backstory%3A%20Zapomnia%C5%82em%20odpowiedzi%20na%20pytania%20zabezpieczaj%C4%85ce%20potrzebne%20do%20zarz%C4%85dzania%20kontem%20Apple%0A%0A%0A%0A%3Ckazi%3E%20jaka%20mo%C5%BCe%20by%C4%87%20moja%20wymarzona%20praca%3F%0A%0A%3Cziomek%3E%20wpisz%20%C5%BCe%20zawsze%20chcia%C5%82e%C5%9B%20by%C4%87%20operatorem%20maszynki%20do%20mi%C4%99sa%0A%09%09%09"></a>
		|				<span class="msg">&nbsp;</span>
		|			</div>
		|			<div class="quote post-content post-body">
		|				It is content of the post.
		|			</div>
		|		</div>
		|""".stripMargin
  )


}
