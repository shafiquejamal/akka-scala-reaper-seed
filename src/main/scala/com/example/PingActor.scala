package com.example

import akka.actor.ActorRef
import com.example.util.WatchedActor

class PingActor(reaper: ActorRef, pongActor: ActorRef) extends WatchedActor(reaper) {
  import PingActor._
  
  var counter = 0

  def receive = {
  	case Initialize => 
	    log.info("In PingActor - starting ping-pong")
  	  pongActor ! PingMessage("ping")	
  	case PongActor.PongMessage(text) =>
  	  log.info("In PingActor - received message: {}", text)
  	  counter += 1
  	  if (counter == 3) context.system.shutdown()
  	  else sender() ! PingMessage("ping")
  }	
}

object PingActor {
  case object Initialize
  case class PingMessage(text: String)
}