package com.macgrewal

import akka.actor.typed.ActorSystem
import akka.http.scaladsl._
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.io.StdIn

trait ServerOnPort8080 {

  def startServer[T](route: Route)
                    (implicit system: ActorSystem[T], ec: ExecutionContext): Unit = {
    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println("Server online at http://localhost:8080/")
    println("Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return

    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}
