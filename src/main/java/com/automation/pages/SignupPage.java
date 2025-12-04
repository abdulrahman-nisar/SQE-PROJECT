package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignupPage extends BasePage {

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

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


    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        type(emailField, email);
    }


    @Step("Enter password")
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    @Step("Click submit button")
    public void clickSubmit() {
        click(submitButton);
    }

    @Step("Click cancel button")
    public void clickCancel() {
        click(cancelButton);
    }


    @Step("Signup with email: {email}")
    public void signup(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        logger.info("Signup performed with email: {}", email);
    }


    @Step("Get error message")
    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getText(errorMessage);
    }


    public boolean isErrorMessageDisplayed() {
        try {
            waitForElementToBeVisible(errorMessage);
            String errorText = getText(errorMessage);
            logger.info("Error message displayed: {}", errorText);
            return true;
        } catch (Exception e) {
            logger.info("No error message displayed");
            return false;
        }
    }


    public String getErrorMessageText() {
        try {
            waitForElementToBeVisible(errorMessage);
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }


    public boolean isErrorMessageDisplayed(String expectedMessage) {
        try {
            waitForElementToBeVisible(errorMessage);
            String actualMessage = getText(errorMessage);
            logger.info("Expected error: '{}', Actual error: '{}'", expectedMessage, actualMessage);
            return actualMessage.contains(expectedMessage);
        } catch (Exception e) {
            logger.info("No error message found");
            return false;
        }
    }


    public boolean isOnSignupPage() {
        return isDisplayed(firstNameField) && isDisplayed(lastNameField);
    }
}

