package com.bbva.mike.tests.nacar

import akka.actor._

/**
 * Created by mikelsanvicente on 10/02/16.
 */
object Main extends App {
  val system = ActorSystem("test-system")

  val master = system.actorOf(Props(new Executor(args(0).toLong, args(1), args(2).toInt, args(3).toBoolean,args(4))),
    name = "master")

  master ! Execute
}