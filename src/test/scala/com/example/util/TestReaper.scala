package com.example.util

import akka.actor.ActorRef

class TestReaper(snooper: ActorRef) extends Reaper {

  override def allSoulsReaped(): Unit = snooper ! "Dead"

}
