package com.example

import akka.actor.{Props, ActorSystem}
import com.example.util.ReaperImpl

object ApplicationMain extends App {

  val system = ActorSystem("MyActorSystem")

  val reaper = system.actorOf(Props[ReaperImpl], "reaper")
  val pongActor = system.actorOf(Props(classOf[PongActor], reaper), "pongActor")
  val pingActor = system.actorOf(Props(classOf[PingActor], reaper, pongActor), "pingActor")

  pingActor ! PingActor.Initialize


}