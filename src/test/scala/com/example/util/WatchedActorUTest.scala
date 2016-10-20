package com.example.util

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import com.example.StopSystemAfterAll
import org.scalatest._

class WatchedActorUTest
  extends TestKit(ActorSystem("testsystem"))
  with FlatSpecLike
  with ShouldMatchers
  with StopSystemAfterAll {

  "A watched actor" should "automatically register itself with a reaper as being watched" in {

    val reaper = TestActorRef[ReaperImpl]
    reaper.underlyingActor.watched shouldBe empty

    val watchedActor = system.actorOf(Props(classOf[TestWatchedActorImpl], reaper), "watched-actor")

    Thread.sleep(50)
    reaper.underlyingActor.watched should contain(watchedActor)

  }

}
