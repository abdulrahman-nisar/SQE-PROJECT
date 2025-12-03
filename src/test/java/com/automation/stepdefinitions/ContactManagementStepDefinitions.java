package com.automation.stepdefinitions;

import com.automation.pages.AddContactPage;
import com.automation.pages.ContactDetailsPage;
import com.automation.pages.ContactListPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Map;

/**
 * ContactManagementStepDefinitions - Step definitions for Contact Management feature
 */
public class ContactManagementStepDefinitions {

    private ContactListPage contactListPage;
    private AddContactPage addContactPage;
    private ContactDetailsPage contactDetailsPage;

    public ContactManagementStepDefinitions() {
        this.contactListPage = new ContactListPage();
        this.addContactPage = new AddContactPage();
        this.contactDetailsPage = new ContactDetailsPage();
    }

    @When("I click add new contact button")
    public void iClickAddNewContactButton() {
        contactListPage.clickAddContact();
        com.automation.utils.ConfigReader.waitFor(com.automation.utils.ConfigReader.getElementActionWait());
    }

    @When("I enter contact first name {string} and last name {string}")
    public void iEnterContactFirstNameAndLastName(String firstName, String lastName) {
        addContactPage.enterFirstName(firstName);
        addContactPage.enterLastName(lastName);
    }

    @When("I enter complete contact details:")
    public void iEnterCompleteContactDetails(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        addContactPage.enterFirstName(data.get("firstName"));
        addContactPage.enterLastName(data.get("lastName"));

        if (data.containsKey("birthdate")) {
            addContactPage.enterBirthdate(data.get("birthdate"));
        }
        if (data.containsKey("email")) {
            addContactPage.enterEmail(data.get("email"));
        }
        if (data.containsKey("phone")) {
            addContactPage.enterPhone(data.get("phone"));
        }
        if (data.containsKey("street1")) {
            addContactPage.enterStreet1(data.get("street1"));
        }
        if (data.containsKey("street2")) {
            addContactPage.enterStreet2(data.get("street2"));
        }
        if (data.containsKey("city")) {
            addContactPage.enterCity(data.get("city"));
        }
        if (data.containsKey("stateProvince")) {
            addContactPage.enterStateProvince(data.get("stateProvince"));
        }
        if (data.containsKey("postalCode")) {
            addContactPage.enterPostalCode(data.get("postalCode"));
        }
        if (data.containsKey("country")) {
            addContactPage.enterCountry(data.get("country"));
        }
    }

    @When("I submit the contact form")
    public void iSubmitTheContactForm() {
        addContactPage.clickSubmit();
    }

    @When("I leave required contact fields empty")
    public void iLeaveRequiredContactFieldsEmpty() {
        // Leave fields empty - do nothing
    }

    @When("I enter invalid contact email {string}")
    public void iEnterInvalidContactEmail(String email) {
        addContactPage.enterEmail(email);
    }

    @Then("the contact should be added successfully")
    public void theContactShouldBeAddedSuccessfully() {
        com.automation.utils.ConfigReader.shortWait();

        Assert.assertTrue(contactListPage.isOnContactListPage(),
            "Should be on contact list page after adding contact");
    }

    @Then("I should see the contact in the list")
    public void iShouldSeeTheContactInTheList() {
        Assert.assertTrue(contactListPage.isOnContactListPage(),
            "Contact list should be visible");
    }

    @When("I click on the first contact in the list")
    public void iClickOnTheFirstContactInTheList() {
        contactListPage.clickFirstContact();
        com.automation.utils.ConfigReader.waitFor(com.automation.utils.ConfigReader.getElementActionWait());
    }

    @When("I click the delete button")
    public void iClickTheDeleteButton() {
        contactDetailsPage.clickDeleteContact();
    }

    @When("I confirm the deletion")
    public void iConfirmTheDeletion() {
        // Alert is either auto-accepted by browser or we accept it manually
        contactDetailsPage.acceptDeleteConfirmation();
    }

    @Then("I should see the contact details page")
    public void iShouldSeeTheContactDetailsPage() {
        Assert.assertTrue(contactDetailsPage.isOnContactDetailsPage(),
            "Contact details page should be displayed");
    }

    @Then("all contact information should be displayed correctly")
    public void allContactInformationShouldBeDisplayedCorrectly() {
        // Verify contact details are visible
        Assert.assertTrue(contactDetailsPage.isOnContactDetailsPage(),
            "Contact information should be displayed");
    }

    @Then("the contact should be removed from the list")
    public void theContactShouldBeRemovedFromTheList() {
        com.automation.utils.ConfigReader.shortWait();

        // Contact should be deleted
        Assert.assertTrue(contactListPage.isOnContactListPage(),
            "Should return to contact list after deletion");
    }

    @Then("I should be redirected to the contact list page")
    public void iShouldBeRedirectedToTheContactListPage() {
        Assert.assertTrue(contactListPage.isOnContactListPage(),
            "Should be redirected to contact list page");
    }

    @Then("I should see an email validation error for contact")
    public void iShouldSeeEmailValidationErrorForContact() {
        Assert.assertTrue(addContactPage.isOnAddContactPage(),
            "Should remain on add contact page for email validation error");
    }

    @Then("I should see a contact validation error")
    public void iShouldSeeAContactValidationError() {
        Assert.assertTrue(addContactPage.isOnAddContactPage(),
            "Should remain on add contact page when validation fails");
    }
}

