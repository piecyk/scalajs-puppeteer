package web.puppeteer

import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.typedarray.ArrayBuffer
import scala.scalajs.js.annotation._

// https://github.com/DefinitelyTyped/DefinitelyTyped/blob/master/types/puppeteer/index.d.ts

object PuppeteerFacade {
  @JSImport("puppeteer", JSImport.Default)
  @js.native
  object Puppeteer extends Puppeteer

  @js.native
  trait Puppeteer extends js.Object {
    def launch(option: LaunchOptions): Promise[Browser] = js.native
  }

  @js.native
  trait Browser extends js.Object {
    def newPage(): Promise[Page] = js.native
    def close(): Promise[Unit] = js.native
  }

  @js.native
  trait Keyboard extends js.Object {
    def `type`(text: String): Promise[Unit] = js.native
  }

  @js.native
  trait Page extends js.Object {
    def click(selector: String): Promise[Unit] = js.native
    def goto(url: String): Promise[Response] = js.native
    def screenshot(options: ScreenshotOptions): Promise[ArrayBuffer] = js.native
    def waitForNavigation(options: NavigationOptions): Promise[Response] = js.native

    val keyboard: Keyboard = js.native
  }

  @js.native
  trait Response extends js.Object {
    def ok(): Boolean = js.native
    def url(): String = js.native
    def status(): Int = js.native
    def text(): Promise[String] = js.native
  }

  trait NavigationOptions extends js.Object {
    val waitUntil: String
  }

  trait ScreenshotOptions extends js.Object {
    val path: String
  }

  trait LaunchOptions extends js.Object {
    val headless: Boolean
    val devtools: Boolean
    val dumpio: Boolean
    val slowMo: Int
  }
}