package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * SignupPage - Page Object Model for Signup/Registration Page
 */
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

    /**
     * Enter first name
     */
    @Step("Enter first name: {firstName}")
    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    /**
     * Enter last name
     */
    @Step("Enter last name: {lastName}")
    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    /**
     * Enter email
     */
    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        type(emailField, email);
    }

    /**
     * Enter password
     */
    @Step("Enter password")
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    /**
     * Click submit button
     */
    @Step("Click submit button")
    public void clickSubmit() {
        click(submitButton);
    }

    /**
     * Click cancel button
     */
    @Step("Click cancel button")
    public void clickCancel() {
        click(cancelButton);
    }

    /**
     * Perform signup
     */
    @Step("Signup with email: {email}")
    public void signup(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        logger.info("Signup performed with email: {}", email);
    }

    /**
     * Get error message
     */
    @Step("Get error message")
    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getText(errorMessage);
    }

    /**
     * Check if error message is displayed
     */
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

    /**
     * Get the error message text
     */
    public String getErrorMessageText() {
        try {
            waitForElementToBeVisible(errorMessage);
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if specific error message is displayed
     */
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

    /**
     * Check if on signup page
     */
    public boolean isOnSignupPage() {
        return isDisplayed(firstNameField) && isDisplayed(lastNameField);
    }
}

