package com.expedia.int.flight

import com.expedia.www.commons.expweb.pagemodel.Page
import org.openqa.selenium.{By, WebElement, WebDriver}

/**
  * Created by v-zoyang on 12/23/2015.
  */
class FlightOneWaySearchResultPage(driver : WebDriver) extends Page(driver){
  val pageIdValue = "page.Flight-Search-Oneway"
  val pageURL = "Flights-Search?"

  def firstResultButtonClick(){
    val firstResultButton: WebElement = driver.findElements(By.className("t-select-btn")).get(0)
    click on firstResultButton
  }

  def results(index: Int): WebElement= {
    driver.findElement(By.id("flightModule"+index))
  }
}