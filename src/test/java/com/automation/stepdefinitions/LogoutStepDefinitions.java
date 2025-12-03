package com.automation.stepdefinitions;

import com.automation.driver.DriverManager;
import com.automation.pages.ContactListPage;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * LogoutStepDefinitions - Step definitions for Logout feature
 */
public class LogoutStepDefinitions {

    private ContactListPage contactListPage;
    private LoginPage loginPage;

    public LogoutStepDefinitions() {
        this.contactListPage = new ContactListPage();
        this.loginPage = new LoginPage();
    }

    @When("I click the logout button")
    public void iClickTheLogoutButton() {
        contactListPage.clickLogout();
    }

    @When("I logout from the application")
    public void iLogoutFromTheApplication() {
        contactListPage.clickLogout();
        com.automation.utils.ConfigReader.shortWait();
    }

    @When("I am redirected to the login page")
    public void iAmRedirectedToTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Should be redirected to login page");
    }

    @Then("I should be logged out successfully")
    public void iShouldBeLoggedOutSuccessfully() {
        com.automation.utils.ConfigReader.shortWait();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login") || loginPage.isOnLoginPage(),
            "User should be redirected to login page after logout");
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "Should be on login page after logout");
    }

    @Then("I should not be able to access protected pages")
    public void iShouldNotBeAbleToAccessProtectedPages() {
        Assert.assertTrue(loginPage.isOnLoginPage(),
            "User should not be able to access protected pages after logout");
    }

    @Then("I should be redirected to login when accessing contact list")
    public void iShouldBeRedirectedToLoginWhenAccessingContactList() {
        // Try to access contact list
        DriverManager.getDriver().get(com.automation.utils.ConfigReader.getAppUrl() + "contactList");
        com.automation.utils.ConfigReader.shortWait();

        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login") || loginPage.isOnLoginPage(),
            "Should be redirected to login when trying to access protected page");
    }

    @Then("the logout button should not be visible")
    public void theLogoutButtonShouldNotBeVisible() {
        Assert.assertFalse(contactListPage.isUserLoggedIn(),
            "Logout button should not be visible on login page");
    }

    @Then("the logout button should be visible on contact list page")
    public void theLogoutButtonShouldBeVisibleOnContactListPage() {
        Assert.assertTrue(contactListPage.isUserLoggedIn(),
            "Logout button should be visible on contact list page");
    }
}

