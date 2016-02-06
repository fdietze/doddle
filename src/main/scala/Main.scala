package doddle

import monix.execution.Scheduler.Implicits.global
import outwatch.dom._
import outwatch.dom.dsl._


object Main {
  def main(args: Array[String]):Unit = {
    val content = div("kloink")
    OutWatch.renderReplace("#container", content).unsafeRunSync()
  }
}
