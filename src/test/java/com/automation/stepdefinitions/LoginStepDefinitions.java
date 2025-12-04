package com.automation.stepdefinitions;

import com.automation.driver.DriverManager;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigurationFileReader;
import com.automation.utils.DatabaseReader;
import com.automation.utils.ExcelReader;
import com.automation.utils.RedisReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

public class LoginStepDefinitions {

    private LoginPage loginPage;

    public LoginStepDefinitions() {
        this.loginPage = new LoginPage();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.openLoginPage();
    }

    @When("I enter valid email {string} and password {string}")
    public void iEnterValidEmailAndPassword(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("I enter invalid email {string} and password {string}")
    public void iEnterInvalidEmailAndPassword(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @When("I enter email {string} and password {string}")
    public void iEnterEmailAndPassword(String email, String password) {
        if (!email.isEmpty()) {
            loginPage.enterEmail(email);
        }
        if (!password.isEmpty()) {
            loginPage.enterPassword(password);
        }
    }


    @When("I enter credentials from database for email {string}")
    public void iEnterCredentialsFromDatabase(String email) {
        Map<String, String> credentials = DatabaseReader.getUserCredentials(email);

        if (credentials.isEmpty()) {
            throw new RuntimeException("No credentials found in database for email: " + email);
        }

        loginPage.enterEmail(credentials.get("email"));
        loginPage.enterPassword(credentials.get("password"));
    }

    @When("I enter credentials from excel row {int}")
    public void iEnterCredentialsFromExcel(int rowIndex) {
        Map<String, String> credentials = ExcelReader.getUserCredentialsFromExcel(rowIndex);

        if (credentials.isEmpty()) {
            throw new RuntimeException("No credentials found in Excel at row: " + rowIndex);
        }

        loginPage.enterEmail(credentials.get("email"));
        loginPage.enterPassword(credentials.get("password"));
    }

    @When("I enter credentials from redis for email {string}")
    public void iEnterCredentialsFromRedis(String email) {
        Map<String, String> credentials = RedisReader.getUserCredentialsFromRedis(email);

        if (credentials.isEmpty()) {
            throw new RuntimeException("No credentials found in Redis for email: " + email);
        }

        loginPage.enterEmail(credentials.get("email"));
        loginPage.enterPassword(credentials.get("password"));
    }


    @When("I click the submit button")
    public void iClickTheSubmitButton() {
        loginPage.clickSubmit();
    }

    @When("I leave email and password fields empty")
    public void iLeaveEmailAndPasswordFieldsEmpty() {
        // Fields are already empty, do nothing
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        // Wait a moment for redirect
        ConfigurationFileReader.mediumWait();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        // Check if we're on contact list page (case-insensitive)
        boolean isLoggedIn = currentUrl.toLowerCase().contains("contactlist");

        System.out.println("DEBUG: Current URL after login = " + currentUrl);
        System.out.println("DEBUG: Login success check = " + isLoggedIn);

        Assert.assertTrue(isLoggedIn,
            "User should be redirected to contact list page after successful login. Current URL: " + currentUrl);
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        ConfigurationFileReader.shortWait(); // Wait for error message to appear

        boolean errorDisplayed = loginPage.isErrorMessageDisplayed();

        if (!errorDisplayed) {
            String actualError = loginPage.getErrorMessageText();
            System.out.println("DEBUG: Expected error message but got: " + actualError);
        }

        Assert.assertTrue(errorDisplayed,
            "Error message should be displayed for invalid login");
    }

    @Then("I should remain on the login page")
    public void iShouldRemainOnTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "User should remain on login page after failed login");
    }

    @Then("the login result should be {string}")
    public void theLoginResultShouldBe(String expectedResult) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String currentUrl = DriverManager.getDriver().getCurrentUrl();

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(currentUrl.contains("contactList") || currentUrl.contains("contactList"),
                "Login should be successful");
        } else {
            Assert.assertTrue(loginPage.isOnLoginPage() || loginPage.isErrorMessageDisplayed(),
                "Login should fail");
        }
    }

    @Then("I should see a validation error")
    public void iShouldSeeAValidationError() {
        // Browser native validation or error message
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Should remain on login page when validation fails");
    }
}

