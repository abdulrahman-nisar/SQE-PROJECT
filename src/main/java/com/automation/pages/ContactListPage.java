package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * ContactListPage - Page Object Model for Contact List Page
 */
public class ContactListPage extends BasePage {

    @FindBy(id = "add-contact")
    private WebElement addContactButton;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(css = "tr.contactTableBodyRow")
    private List<WebElement> contactRows;

    @FindBy(id = "myTable")
    private WebElement contactTable;

    @FindBy(css = "p")
    private WebElement welcomeMessage;

    /**
     * Click add contact button
     */
    @Step("Click add contact button")
    public void clickAddContact() {
        click(addContactButton);
    }

    /**
     * Click logout button
     */
    @Step("Click logout button")
    public void clickLogout() {
        click(logoutButton);
    }

    /**
     * Get number of contacts
     */
    @Step("Get number of contacts")
    public int getNumberOfContacts() {
        return contactRows.size();
    }

    /**
     * Check if contact table is displayed
     */
    public boolean isContactTableDisplayed() {
        return isDisplayed(contactTable);
    }

    /**
     * Check if on contact list page
     */
    public boolean isOnContactListPage() {
        return isDisplayed(addContactButton) && isDisplayed(logoutButton);
    }

    /**
     * Get welcome message
     */
    @Step("Get welcome message")
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    /**
     * Click on first contact in list
     */
    @Step("Click on first contact")
    public void clickFirstContact() {
        if (!contactRows.isEmpty()) {
            click(contactRows.get(0));
        }
    }

    /**
     * Check if add contact button is visible
     */
    public boolean isAddContactButtonVisible() {
        return isDisplayed(addContactButton);
    }

    /**
     * Verify user is logged in
     */
    @Step("Verify user is logged in")
    public boolean isUserLoggedIn() {
        return isDisplayed(logoutButton) && isDisplayed(addContactButton);
    }
}

