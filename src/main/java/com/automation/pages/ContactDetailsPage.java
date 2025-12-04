package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactDetailsPage extends BasePage {

    @FindBy(id = "edit-contact")
    private WebElement editContactButton;

    @FindBy(id = "delete")
    private WebElement deleteContactButton;

    @FindBy(id = "return")
    private WebElement returnButton;

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


    @Step("Click edit contact button")
    public void clickEditContact() {
        click(editContactButton);
    }


    @Step("Click delete contact button")
    public void clickDeleteContact() {
        click(deleteContactButton);
    }


    @Step("Click return button")
    public void clickReturn() {
        click(returnButton);
    }


    @Step("Get first name")
    public String getFirstName() {
        return getText(firstNameField);
    }


    @Step("Get last name")
    public String getLastName() {
        return getText(lastNameField);
    }


    @Step("Get email")
    public String getEmail() {
        return getText(emailField);
    }


    @Step("Get phone")
    public String getPhone() {
        return getText(phoneField);
    }


    public boolean isOnContactDetailsPage() {
        return isDisplayed(editContactButton) && isDisplayed(deleteContactButton);
    }


    @Step("Accept delete confirmation")
    public void acceptDeleteConfirmation() {
        acceptAlert();
    }
}

