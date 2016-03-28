package com.bbva.mike.tests.nacar

/**
 * Created by mikelsanvicente on 22/03/16.
 */
object UserService {

}

trait User
case class UserA(name:String, surname:String) extends User
case class UserB(name:String) extends User