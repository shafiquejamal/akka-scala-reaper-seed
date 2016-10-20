package com.example.util

import akka.actor.{Actor, ActorLogging, ActorRef}
import com.example.util.Reaper.WatchMe

abstract class WatchedActor(reaper: ActorRef) extends Actor with ActorLogging {

  reaper ! WatchMe(self)

}
