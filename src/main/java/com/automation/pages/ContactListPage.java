package com.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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


    @Step("Click add contact button")
    public void clickAddContact() {
        click(addContactButton);
    }


    @Step("Click logout button")
    public void clickLogout() {
        click(logoutButton);
    }


    @Step("Get number of contacts")
    public int getNumberOfContacts() {
        return contactRows.size();
    }


    public boolean isContactTableDisplayed() {
        return isDisplayed(contactTable);
    }


    public boolean isOnContactListPage() {
        return isDisplayed(addContactButton) && isDisplayed(logoutButton);
    }


    @Step("Get welcome message")
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }


    @Step("Click on first contact")
    public void clickFirstContact() {
        if (!contactRows.isEmpty()) {
            click(contactRows.get(0));
        }
    }


    public boolean isAddContactButtonVisible() {
        return isDisplayed(addContactButton);
    }


    @Step("Verify user is logged in")
    public boolean isUserLoggedIn() {
        return isDisplayed(logoutButton) && isDisplayed(addContactButton);
    }
}

