import mill._
import mill.scalajslib._
import scalalib._

object web extends ScalaJSModule {
  def scalaJSVersion = "0.6.22"
  def scalaVersion = "2.12.4"
  def scalacOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-encoding", "utf8",
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-P:scalajs:sjsDefinedByDefault"
  )
  def mainClass = Some("web.Main")
  def moduleKind = ModuleKind.CommonJSModule

  def ivyDeps = Agg(
    ivy"org.typelevel::cats-core::1.1.0"
  )
}
