package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddContactPage extends BasePage {

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "birthdate")
    private WebElement birthdateField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "street1")
    private WebElement street1Field;

    @FindBy(id = "street2")
    private WebElement street2Field;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "stateProvince")
    private WebElement stateProvinceField;

    @FindBy(id = "postalCode")
    private WebElement postalCodeField;

    @FindBy(id = "country")
    private WebElement countryField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "error")
    private WebElement errorMessage;


    @Step("Enter first name: {firstName}")
    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }


    @Step("Enter last name: {lastName}")
    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }


    @Step("Enter birthdate: {birthdate}")
    public void enterBirthdate(String birthdate) {
        type(birthdateField, birthdate);
    }


    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        type(emailField, email);
    }


    @Step("Enter phone: {phone}")
    public void enterPhone(String phone) {
        type(phoneField, phone);
    }


    @Step("Enter street address 1: {street1}")
    public void enterStreet1(String street1) {
        type(street1Field, street1);
    }

    @Step("Enter street address 2: {street2}")
    public void enterStreet2(String street2) {
        type(street2Field, street2);
    }


    @Step("Enter city: {city}")
    public void enterCity(String city) {
        type(cityField, city);
    }


    @Step("Enter state/province: {stateProvince}")
    public void enterStateProvince(String stateProvince) {
        type(stateProvinceField, stateProvince);
    }


    @Step("Enter postal code: {postalCode}")
    public void enterPostalCode(String postalCode) {
        type(postalCodeField, postalCode);
    }


    @Step("Enter country: {country}")
    public void enterCountry(String country) {
        type(countryField, country);
    }


    @Step("Click submit button")
    public void clickSubmit() {
        click(submitButton);
    }


    @Step("Click cancel button")
    public void clickCancel() {
        click(cancelButton);
    }


    @Step("Add contact: {firstName} {lastName}")
    public void addContact(String firstName, String lastName, String email, String phone) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPhone(phone);
        clickSubmit();
        logger.info("Contact added: {} {}", firstName, lastName);
    }


    @Step("Add contact with full details")
    public void addContactWithFullDetails(String firstName, String lastName, String birthdate,
                                          String email, String phone, String street1, String street2,
                                          String city, String state, String postalCode, String country) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterBirthdate(birthdate);
        enterEmail(email);
        enterPhone(phone);
        enterStreet1(street1);
        enterStreet2(street2);
        enterCity(city);
        enterStateProvince(state);
        enterPostalCode(postalCode);
        enterCountry(country);
        clickSubmit();
        logger.info("Contact added with full details: {} {}", firstName, lastName);
    }


    @Step("Get error message")
    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getText(errorMessage);
    }


    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }


    public boolean isOnAddContactPage() {
        return isDisplayed(firstNameField) && isDisplayed(submitButton);
    }
}

