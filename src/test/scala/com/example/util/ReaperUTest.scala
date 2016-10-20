package com.example.util

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.example.StopSystemAfterAll
import org.scalatest.{FlatSpecLike, ShouldMatchers}

class ReaperUTest
  extends TestKit(ActorSystem("testsystem"))
  with FlatSpecLike
  with ShouldMatchers
  with StopSystemAfterAll
  with ImplicitSender {

  import Reaper._

  override def afterAll(): Unit = {
    system.shutdown()
    super.afterAll()
  }

  trait ReaperFixture {
    val reaper = system.actorOf(Props(new TestReaper(testActor)))
    val actor1 = TestProbe()
    val actor2 = TestProbe()
    val actor3 = TestProbe()
    val actor4 = TestProbe()
  }

  "The Reaper" should "call the allSoulsReaped method after all watched actors are dead" in new ReaperFixture {
    reaper ! WatchMe(actor3.ref)
    reaper ! WatchMe(actor1.ref)

    system.stop(actor1.ref)
    system.stop(actor3.ref)

    expectMsg("Dead")
  }

  it should "fail to call the allSoulsReaped method if not all watched actors are dead" in new ReaperFixture {
    reaper ! WatchMe(actor3.ref)
    reaper ! WatchMe(actor1.ref)

    system.stop(actor1.ref)
    expectNoMsg()
  }

}
