package com.bbva.mike.tests.nacar

/**
 * Created by mikelsanvicente on 17/03/16.
 */
sealed trait TestMessage
case object Execute extends TestMessage
case object SendMessage extends TestMessage
case object SendSuccess extends TestMessage
case object SendError extends TestMessage
