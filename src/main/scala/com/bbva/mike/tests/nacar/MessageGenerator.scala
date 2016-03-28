package com.bbva.mike.tests.nacar

import scala.compat.Platform

/**
 * Created by mikelsanvicente on 17/03/16.
 */
object MessageGenerator {
  val r = scala.util.Random
  def generateRandomMessage():String = {
    val ua =  "Ua" + r.nextInt(100).toString
    val service = "Serv"+ r.nextInt(5000).toString
    val currentTime = Platform.currentTime
    val message = Map[String,Any]("pro" -> ua, "n" -> service,
      "ts" -> currentTime, "d" -> r.nextInt(1 + (currentTime % 1000000).toInt))
    scala.util.parsing.json.JSONObject(message).toString()
  }
}
