package com.expedia.int.flight

import com.expedia.www.commons.expweb.pagemodel.Page
import org.openqa.selenium.{By, WebElement, WebDriver}
import scala.util.Try

/**
  * Created by v-zoyang on 12/23/2015.
  */
class FlightUDPage(driver : WebDriver) extends Page(driver){
  val pageIdValue = "page.Flight.Ratedetails"
  val pageURL = "Details?"

  def continueBookButtonClick(){
    val continueBookButton: WebElement =Try(driver.findElement(By.id("FlightUDPBookNowButton1"))).getOrElse(null)
    click on continueBookButton
  }
}