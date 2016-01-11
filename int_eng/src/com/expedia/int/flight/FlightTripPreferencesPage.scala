package com.expedia.int.flight

import com.expedia.www.commons.expweb.pagemodel.Page
import org.openqa.selenium.{By, WebElement, WebDriver}
import scala.util.Try

/**
  * Created by v-zoyang on 12/23/2015.
  */
class FlightTripPreferencesPage(driver : WebDriver) extends Page(driver){
  val pageIdValue = "TripPreferences-Flight"
  val pageURL = "TripPreferences-Flight"

  object TravellerDetails{
    val firstName: TextField = Try(textField("tp_primary_contact_first_name_input_0")(driver)).getOrElse(null)
    val lastName: TextField = Try(textField("tp_primary_contact_last_name_input_0")(driver)).getOrElse(null)
    val phoneNumber: TextField = Try(textField("preferred_phone_number_input_0")(driver)).getOrElse(null)
    val gender: SingleSel = Try(singleSel("tsa_gender_input_0")(driver)).getOrElse(null)
    val birthMonth: SingleSel = Try(singleSel("birth_month_input_0")(driver)).getOrElse(null)
    val birthDay: SingleSel = Try(singleSel("birth_day_input_0")(driver)).getOrElse(null)
    val birthYear: SingleSel = Try(singleSel("birth_year_input_0")(driver)).getOrElse(null)
  }

  def continueBookButtonClick(){
    val continueBookButton: WebElement =Try(driver.findElement(By.id("submit_button"))).getOrElse(null)
    click on continueBookButton
  }
}