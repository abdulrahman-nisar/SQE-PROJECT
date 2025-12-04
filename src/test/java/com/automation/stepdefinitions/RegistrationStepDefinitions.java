package com.automation.stepdefinitions;

import com.automation.driver.DriverManager;
import com.automation.pages.LoginPage;
import com.automation.pages.SignupPage;
import com.automation.utils.ConfigurationFileReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

public class RegistrationStepDefinitions {

    private LoginPage loginPage;
    private SignupPage signupPage;
    private static String lastGeneratedEmail; // Store last generated email

    public RegistrationStepDefinitions() {
        this.loginPage = new LoginPage();
        this.signupPage = new SignupPage();
    }

    private String generateUniqueEmail(String baseEmail) {
        long timestamp = System.currentTimeMillis();
        String[] parts = baseEmail.split("@");
        if (parts.length == 2) {
            return parts[0] + "." + timestamp + "@" + parts[1];
        }
        return "user." + timestamp + "@test.com";
    }

    @When("I click the signup button")
    public void iClickTheSignupButton() {
        loginPage.clickSignup();
        ConfigurationFileReader.waitFor(ConfigurationFileReader.getElementActionWait());
    }

    @When("I enter registration details:")
    public void iEnterRegistrationDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        // Generate unique email to avoid "email already exists" errors
        String uniqueEmail = generateUniqueEmail(data.get("email"));
        lastGeneratedEmail = uniqueEmail; // Store for potential reuse

        System.out.println("DEBUG: Using unique email for registration: " + uniqueEmail);

        signupPage.enterFirstName(data.get("firstName"));
        signupPage.enterLastName(data.get("lastName"));
        signupPage.enterEmail(uniqueEmail);
        signupPage.enterPassword(data.get("password"));
    }

    @When("I enter first name {string}, last name {string}, email {string}, and password {string}")
    public void iEnterFirstNameLastNameEmailAndPassword(String firstName, String lastName, String email, String password) {
        // Generate unique email to avoid "email already exists" errors
        String uniqueEmail = generateUniqueEmail(email);
        lastGeneratedEmail = uniqueEmail; // Store for potential reuse

        System.out.println("DEBUG: Using unique email for registration: " + uniqueEmail);

        signupPage.enterFirstName(firstName);
        signupPage.enterLastName(lastName);
        signupPage.enterEmail(uniqueEmail);
        signupPage.enterPassword(password);
    }

    @When("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        signupPage.clickSubmit();
    }

    @When("I enter registration details with an existing email")
    public void iEnterRegistrationDetailsWithExistingEmail() {
        signupPage.enterFirstName("Test");
        signupPage.enterLastName("User");
        signupPage.enterEmail("existing@test.com");
        signupPage.enterPassword("Password123");
        signupPage.clickSubmit();
    }

    @When("I enter an invalid email format {string}")
    public void iEnterInvalidEmailFormat(String email) {
        signupPage.enterEmail(email);
    }

    @When("I fill other registration fields correctly")
    public void iFillOtherRegistrationFieldsCorrectly() {
        signupPage.enterFirstName("Test");
        signupPage.enterLastName("User");
        signupPage.enterPassword("Password123");
    }

    @When("I leave required fields empty")
    public void iLeaveRequiredFieldsEmpty() {
        // Leave fields empty
    }

    @Then("I should be registered successfully")
    public void iShouldBeRegisteredSuccessfully() {
        ConfigurationFileReader.mediumWait(); // Wait for registration to process

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        // Check if we're on contact list page (case-insensitive)
        boolean isSuccess = currentUrl.toLowerCase().contains("contactlist");

        System.out.println("DEBUG: Current URL after registration = " + currentUrl);
        System.out.println("DEBUG: Success check = " + isSuccess);

        Assert.assertTrue(isSuccess,
            "User should be registered successfully. Current URL: " + currentUrl);
    }

    @Then("the registration result should be {string}")
    public void theRegistrationResultShouldBe(String expectedResult) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String currentUrl = DriverManager.getDriver().getCurrentUrl();

        if (expectedResult.equalsIgnoreCase("success")) {
            Assert.assertTrue(currentUrl.contains("contactList"),
                "Registration should be successful");
        } else {
            Assert.assertTrue(signupPage.isOnSignupPage() || signupPage.isErrorMessageDisplayed(),
                "Registration should fail");
        }
    }

    @Then("I should see an error message indicating email already exists")
    public void iShouldSeeErrorMessageEmailExists() {
        ConfigurationFileReader.shortWait(); // Wait for error message to appear

        // Check if error message is displayed and contains the expected text
        boolean errorDisplayed = signupPage.isErrorMessageDisplayed("Email address is already in use");

        if (!errorDisplayed) {
            // Try to get the actual error message for debugging
            String actualError = signupPage.getErrorMessageText();
            System.out.println("DEBUG: Expected error message not found. Actual error: " + actualError);
        }

        Assert.assertTrue(errorDisplayed,
            "Error message 'Email address is already in use' should be displayed for existing email");
    }

    @Then("I should see an email validation error")
    public void iShouldSeeEmailValidationError() {
        Assert.assertTrue(signupPage.isOnSignupPage(),
            "Should remain on signup page when email validation fails");
    }

    @Then("I should see validation errors for required fields")
    public void iShouldSeeValidationErrorsForRequiredFields() {
        Assert.assertTrue(signupPage.isOnSignupPage(),
            "Should remain on signup page when required fields are empty");
    }

    @Then("I should see the contact list page")
    public void iShouldSeeTheContactListPage() {
        ConfigurationFileReader.shortWait();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("contactList"),
            "Contact list page should be displayed");
    }
}

