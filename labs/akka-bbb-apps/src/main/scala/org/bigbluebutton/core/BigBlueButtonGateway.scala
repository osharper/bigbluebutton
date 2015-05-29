package org.bigbluebutton.core

import org.bigbluebutton.core.api._
import java.util.concurrent.CountDownLatch
import akka.actor.{ ActorSystem, Props }

class BigBlueButtonGateway(system: ActorSystem, outGW: MessageOutGateway) {

  val bbbActor = system.actorOf(
    BigBlueButtonActor.props(system, outGW), "bigbluebutton-actor")

  def accept(msg: InMessage): Unit = {
    bbbActor ! msg
  }

  def acceptKeepAlive(msg: KeepAliveMessage): Unit = {
    bbbActor ! msg
  }

}
