package com.expedia.int.flight

import com.expedia.www.commons.expweb.pagemodel.Page
import org.openqa.selenium.{By, WebElement, WebDriver}
import org.scalatest.selenium.WebBrowser.{TextField, SingleSel, click}
import scala.util.Try

/**
  * Created by v-zoyang on 12/23/2015.
  */
class Homepage(driver : WebDriver) extends Page(driver){
  val pageIdValue = "Homepage"
  val pageURL = ""

  def flightTabClick(){
    val flightTab: WebElement =Try(driver.findElement(By.id("tab-flight-tab"))).getOrElse(null)
    click on flightTab
  }

  def flightOnewayClick(): Unit ={
    val flightOneway: WebElement =Try(driver.findElement(By.id("flight-type-one-way-label"))).getOrElse(null)
    click on flightOneway
  }

  object SearchWizard{
    val adult: SingleSel = Try(singleSel("flight-adults")(driver)).getOrElse(null)
    val children: SingleSel = Try(singleSel("flight-children")(driver)).getOrElse(null)
    val departure: TextField = Try(textField("flight-origin")(driver)).getOrElse(null)
    val destination: TextField = Try(textField("flight-destination")(driver)).getOrElse(null)
    val departureTime: TextField = Try(textField("flight-departing")(driver)).getOrElse(null)
  }

  def searchButtonClick(){
    val searchButton: WebElement =Try(driver.findElement(By.id("search-button"))).getOrElse(null)
    click on searchButton
  }
}
