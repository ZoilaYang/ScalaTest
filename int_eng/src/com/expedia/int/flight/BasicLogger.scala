package com.expedia.int.flight

import scala.collection.mutable.Map


/**
  * Created by v-zoyang on 12/29/2015.
  */
class BasicLogger {

  var messages = Map[String,String]("error"->"","warning"->"","info"->"","step"->"","debug"->"","all"->"")

  def addMessage(logType: String,message: String){
    println("["+logType.toUpperCase()+"] "+message)
    messages(logType) += message
    messages("all") += "(#" + logType + ") #" + message + "; "
  }

  def error(message: String){
    addMessage("error",message)
  }

  def warning(message: String){
    addMessage("warning",message)
  }

  def info(message: String){
    addMessage("info",message)
  }

  def step(message: String){
    addMessage("step",message)
  }

  def debug(message: String){
    addMessage("debug",message)
  }

  def all(logType: String,message: String){
    addMessage(logType,message)
  }

}