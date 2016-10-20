package com.example.util

class ReaperImpl extends Reaper {

  override def allSoulsReaped(): Unit = context.system.shutdown()

}
