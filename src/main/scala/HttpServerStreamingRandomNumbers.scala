package com.macgrewal

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContextExecutor
import scala.util.Random

object HttpServerStreamingRandomNumbers extends ServerOnPort8080 {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "RandomNumbers")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    // streams are re-usable so we can define it here
    // and use it for every request
    val numbers = Source.fromIterator(() => Iterator.continually(Random.nextInt()))

    val route =
      path("random") {
        get {
          complete(
            HttpEntity(
              ContentTypes.`text/plain(UTF-8)`,
              // transform each number to a chunk of bytes
              numbers.map(n => ByteString(s"$n\n"))
            )
          )
        }
      }

    startServer(route)
  }
}