package web

import scala.scalajs.js.Promise
// import scala.scalajs.js.Thenable.Implicits._
// import scala.concurrent.ExecutionContext.Implicits.global
// import scala.concurrent.Future

// import scala.util.{Success, Failure}
import monix.execution.Scheduler.Implicits.global
import monix.eval.Task

object HelloG {
  import web.puppeteer.PuppeteerFacade._

  implicit def promiseToTask[T](promise: Promise[T]): Task[T] =
    Task.fromFuture(promise.toFuture)

  def pageHandler(page: Page): Task[Unit] = {
    val query = "scalajs"
    for {
      _ <- page.goto("https://www.google.com")
      _ <- page.click("#lst-ib")
      _ <- page.keyboard.`type`(query)
      _ <- page.click("#tsf > div.tsf-p > div.jsb > center > input[type=\"submit\"]:nth-child(1)")
      _ <- page.waitForNavigation(new NavigationOptions { val waitUntil = "networkidle2" })
      _ <- page.evaluate("document.querySelector(\"#resultStats\").innerText") flatMap { text => {
        println(s"$text for '$query'")
        Task.unit
      }}
    } yield ()
  }

  def lunch(pageHandler: Page => Task[Unit]): Task[Unit] = {
    Puppeteer.launch(new LaunchOptions {
      val headless = true
      val devtools = false
      val dumpio = false
      val slowMo = 0
    }) flatMap { browser => browser.newPage() flatMap pageHandler doOnFinish {
      case None =>
        browser.close()
      case Some(ex) =>
        println(ex)
        browser.close()
    }}
  }
}

object Main extends App {
  val vmName = sys.props("java.vm.name")
  println(s"Hello = $vmName")

  HelloG.lunch(HelloG.pageHandler).runAsync map { _ => println("ByeG") }
}
