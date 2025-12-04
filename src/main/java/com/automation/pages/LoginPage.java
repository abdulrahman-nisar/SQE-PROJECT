package com.automation.pages;

import com.automation.utils.ConfigurationFileReader;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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


    @Step("Open login page")
    public void openLoginPage() {
        navigateToUrl(ConfigurationFileReader.getAppUrl());
        waitForPageLoad();
        logger.info("Opened login page");
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


    @Step("Click signup button")
    public void clickSignup() {
        click(signupButton);
    }


    @Step("Login with email: {email}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
        logger.info("Login performed with email: {}", email);
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


    public boolean isOnLoginPage() {
        return isDisplayed(submitButton) && isDisplayed(emailField);
    }
}

