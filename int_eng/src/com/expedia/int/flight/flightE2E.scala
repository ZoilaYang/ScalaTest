package com.expedia.int.flight

import com.expedia.www.commons.expweb.cko.pagemodel.AirPaymentsPage
import com.expedia.www.commons.expweb.helper.PointsOfSale.PointOfSale
import com.expedia.www.commons.expweb.helper.{ExpwebUriBuilder, PointsOfSale}
import com.expedia.www.commons.expweb.pagemodel.HomePage
import com.expedia.www.commons.tags.AcceptanceTest
import com.expedia.www.commons.test.AcceptanceSpec
import com.expedia.www.commons.ui.browser.BrowserManager
import org.openqa.selenium.{By, WebElement, WebDriver}
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.Tables.Table
import org.scalatest.time.{Span, Seconds}
import com.expedia.int.flight

import scala.util.Try

/**
  * Created by v-zoyang on 12/16/2015.
  */

class flightE2E extends AcceptanceSpec with BrowserManager with ExpwebUriBuilder{
  val logger = new BasicLogger()

  feature("flightE2E case") {

    val searchData = Table(
      ("pos", "departure", "destination", "departureTime", "adultNumber"),
      (PointsOfSale.fr_CA_TV, "SEA", "LAX", "07/01/2016", "1"),
      (PointsOfSale.fr_CA, "SEA", "LAX", "07/01/2016", "1"))

    forAll(searchData) {
      (pos: PointsOfSale.PointOfSale, departure: String, destination: String, departureTime: String, adultNumber: String) =>
        scenario(s"Flight one way flow from Homepage to Payment page $pos", AcceptanceTest) {

          Given("I go to Homepage and do a flight search")
          logger.step("Do a flight one way search on Homepage...")
          goToHomePage(pos)
          Thread.sleep(10000)
          val homePage = new Homepage(webDriver)
          eventually {
            homePage.pageId should be(homePage.pageIdValue)
          }
          homePage.flightTabClick
          homePage.flightOnewayClick
          val sw = homePage.SearchWizard
          sw.adult.value = adultNumber
          sw.departure.value = departure
          sw.destination.value = destination
          sw.departureTime.value = departureTime
          homePage.searchButtonClick

          Then ("I select the first flight on Flight Search Result Page and process to TP page")
          logger.step("Select the first flight on Flight Search Result Page...")
          val flightSearchResultPage = new FlightOneWaySearchResultPage(webDriver)
          eventually {
            flightSearchResultPage.pageId should be(flightSearchResultPage.pageIdValue)
          }
          var flight = Try(flightSearchResultPage.results(0)).getOrElse(null)
          if (flight == null){
            eventually (timeout(Span(30, Seconds)), interval(Span(1, Seconds))) {
              flight = Try(flightSearchResultPage.results(0)).getOrElse(null)
            }
          }
          flightSearchResultPage.firstResultButtonClick()

          logger.step("Click continue booking button on Flight UDP page...")
          switchToNewWindow(webDriver)
          val flightUDPage = new FlightUDPage(webDriver)
          flightUDPage.continueBookButtonClick()

          Then ("I fill all the information on TP page")
          logger.step("Fill the information on TP page...")
          val flightTripPreferencesPage = new FlightTripPreferencesPage(webDriver)
          eventually {
            flightTripPreferencesPage.pageTitle.matches("Traveller Information")
          }
          val td = flightTripPreferencesPage.TravellerDetails
          td.firstName.value = "zoila"
          td.lastName.value = "yang"
          td.phoneNumber.value = "12345678909"
          td.gender.value = "FEMALE"
          td.birthMonth.value = "5"
          td.birthDay.value = "7"
          td.birthYear.value = "1992"
          flightTripPreferencesPage.continueBookButtonClick()

          And ("I fill all the information on Payment Page")
          logger.step("Fill the information on Payment page...")
          val airPaymentsPage = new AirPaymentsPage(webDriver)
          eventually {
            airPaymentsPage.pageId should be(airPaymentsPage.pageIdValue)
          }
          //      airPaymentsPage.PaymentsDetails.insurancePiid.value = "none"
          //      airPaymentsPage.PaymentsDetails.cardNumber.value = "4111111111111111"
          //      airPaymentsPage.PaymentsDetails.cardType.value = "Visa"
          //      airPaymentsPage.PaymentsDetails.expirationMonth.value = "3"
          //      airPaymentsPage.PaymentsDetails.expirationYear.value = "2015"
          //      airPaymentsPage.PaymentsDetails.securityCodeText.value = "123"
          //      airPaymentsPage.PaymentsDetails.cardHolderName.value = "Test Name"
          //
          //      eventually {
          //        airPaymentsPage.PaymentBillingAddress.country.value should be("USA")
          //        airPaymentsPage.PaymentBillingAddress.postalCode should be('displayed)
          //      }
          //      airPaymentsPage.PaymentBillingAddress.street.value = "1 John Street"
          //      airPaymentsPage.PaymentBillingAddress.street2.value = "New York"
          //      airPaymentsPage.PaymentBillingAddress.cityTownVillage.value = "London"
          //      airPaymentsPage.PaymentBillingAddress.countryStateProvince.value = "AS"
          //      airPaymentsPage.PaymentBillingAddress.postalCode.value = "12222222"
        }
    }
  }

  def goToHomePage(pos: PointOfSale) {
    val url = buildUri(pos)
    logger.info("Go to url " + url)
    go to url
  }
  def switchToNewWindow(driver : WebDriver){
    //    val currentWindow = driver.getWindowHandle
    val handles = driver.getWindowHandles
    val it = handles.toArray()
    logger.info("Switch window to new page...")
    driver.close()
    driver.switchTo().window(it(it.length-1).toString)
    Thread.sleep(10000)
  }
}



