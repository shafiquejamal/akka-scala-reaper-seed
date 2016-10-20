package com.example

import akka.actor.ActorRef
import com.example.util.WatchedActor

class PongActor(reaper: ActorRef) extends WatchedActor(reaper) {
  import PongActor._

  def receive = {
  	case PingActor.PingMessage(text) => 
  	  log.info("In PongActor - received message: {}", text)
  	  sender() ! PongMessage("pong")
  }	
}

object PongActor {
  case class PongMessage(text: String)
}
