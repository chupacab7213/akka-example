package com.bbva.mike.tests.nacar

import akka.actor.{ ActorLogging, Actor}
import play.api.libs.ws.ning.NingWSClient
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by mikelsanvicente on 21/03/16.
 */
class ApiSender(endpoint:String) extends Actor  with ActorLogging {

  val client = NingWSClient()

  def receive = {
    case SendMessage =>
      val executor = sender()

      val responseFuture = client.url(endpoint).post(MessageGenerator.generateRandomMessage())
      responseFuture.onComplete(response => {
                  if (response.isFailure) {
                      log.warning(response.failed.get.toString)
                      executor ! SendError
                    } else
                      executor ! SendSuccess
                })
  }
}
