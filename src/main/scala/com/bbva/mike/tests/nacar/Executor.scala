package com.bbva.mike.tests.nacar

import akka.actor.{Props, ActorLogging, Actor}
import akka.routing.RoundRobinRouter

/**
 * Created by mikelsanvicente on 17/03/16.
 */

class Executor(numberOfMessages:Long, endpoint:String, threads:Int,useApi:Boolean, topic:String)
  extends Actor with ActorLogging {

  val worker = if(useApi) {
    context.actorOf(
      Props[ApiSender](new ApiSender(endpoint)), name = "apiSender")
  } else {
    context.actorOf(
      Props[KafkaSender](new KafkaSender(topic)).withRouter(RoundRobinRouter(threads)), name = "workerRouter")
  }

  var nrOfTasks: Long = _
  var nrOfResults: Long = _
  var nrOfErrors: Long = _

  def receive = {
    case Execute =>
      if(numberOfMessages > 1000)
        nrOfTasks = 1000
      else
        nrOfTasks = numberOfMessages
      1L to nrOfTasks foreach { _ =>
        worker ! SendMessage
      }
    case SendSuccess =>
      nrOfResults += 1
      checkIfFinished()
    case SendError =>
      nrOfErrors += 1
      checkIfFinished()
  }

  def checkIfFinished(): Unit = {
    if(numberOfMessages > nrOfTasks) {
      nrOfTasks += 1
      worker ! SendMessage
    }
    if (nrOfResults + nrOfErrors == numberOfMessages) {
      // Stops this actor and all its supervised children
      log.warning(nrOfErrors + " errors")
      context.stop(self)
      context.system.shutdown()
      System.exit(0)
    }
  }
}