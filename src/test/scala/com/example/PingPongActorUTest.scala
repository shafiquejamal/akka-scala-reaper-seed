package com.example

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit}
import com.example.util.ReaperImpl
import org.scalatest.{FlatSpecLike, BeforeAndAfterAll, Matchers, WordSpecLike}
 
class PingPongActorUTest(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with FlatSpecLike
  with Matchers
  with BeforeAndAfterAll {
 
  def this() = this(ActorSystem("MySpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  val reaper = system.actorOf(Props[ReaperImpl], "reaper")
  val pongActor = system.actorOf(Props(classOf[PongActor], reaper), "pongActor")
  val pingActor = system.actorOf(Props(classOf[PingActor], reaper, pongActor), "pingActor")

  "A Ping actor" should "send back a ping on a pong" in {
      pingActor ! PongActor.PongMessage("pong")
      expectMsg(PingActor.PingMessage("ping"))
  }

  "A Pong actor" should "send back a pong on a ping" in {
      pongActor ! PingActor.PingMessage("ping")
      expectMsg(PongActor.PongMessage("pong"))
  }

}
