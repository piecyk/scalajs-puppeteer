package web

// import cats.implicits._

import scala.scalajs.js.Thenable.Implicits._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object HelloG {
  import web.puppeteer.PuppeteerFacade._

  def pageHandler(page: Page): Future[Unit] = {
    for {
      _ <- page.goto("https://www.google.pl")
      _ <- page.click("#lst-ib")
      _ <- page.keyboard.`type`("scalajs")
      _ <- page.click("#tsf > div.tsf-p > div.jsb > center > input[type=\"submit\"]:nth-child(1)")
      _ <- page.waitForNavigation(new NavigationOptions { val waitUntil = "networkidle0" })
    } yield ()
  }

  def lunch(pageHandler: Page => Future[Unit]): Future[Unit] = {
    Puppeteer.launch(new LaunchOptions {
      val headless = false
      val devtools = false
      val dumpio = false
      val slowMo = 0
    }) flatMap { browser =>
      browser.newPage() flatMap pageHandler flatMap { _ => browser.close() }
    }
  }
}

object Main extends App {
  val vmName = sys.props("java.vm.name")
  println(s"Hello = $vmName")

  HelloG.lunch(HelloG.pageHandler) map { _ => println("ByeG") }
}
