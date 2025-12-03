package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage - Page Object Model for Login Page
 */
public class LoginPage extends BasePage {

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "signup")
    private WebElement signupButton;

    @FindBy(id = "error")
    private WebElement errorMessage;

    /**
     * Navigate to login page
     */
    @Step("Open login page")
    public void openLoginPage() {
        navigateToUrl(com.automation.utils.ConfigReader.getAppUrl());
        waitForPageLoad();
        logger.info("Opened login page");
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
     * Click signup button
     */
    @Step("Click signup button")
    public void clickSignup() {
        click(signupButton);
    }

    /**
     * Perform login
     */
    @Step("Login with email: {email}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        logger.info("Login performed with email: {}", email);
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
     * Check if on login page
     */
    public boolean isOnLoginPage() {
        return isDisplayed(submitButton) && isDisplayed(emailField);
    }
}

