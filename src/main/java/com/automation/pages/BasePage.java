package com.automation.pages;

import com.automation.driver.DriverManager;
import com.automation.utils.ConfigurationFileReader;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigurationFileReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }


    @Step("Click on element: {element}")
    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
        logger.info("Clicked on element: {}", element);
    }


    @Step("Type '{text}' into element")
    protected void type(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
        logger.info("Typed '{}' into element", text);
    }


    @Step("Get text from element")
    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        String text = element.getText();
        logger.info("Got text '{}' from element", text);
        return text;
    }


    @Step("Check if element is displayed")
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }


    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    protected void waitForElementToBeInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


    @Step("Navigate to URL: {url}")
    protected void navigateToUrl(String url) {
        driver.get(url);
        logger.info("Navigated to URL: {}", url);
    }


    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }


    protected String getPageTitle() {
        return driver.getTitle();
    }


    protected Object executeJavaScript(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script, args);
    }


    @Step("Scroll to element")
    protected void scrollToElement(WebElement element) {
        executeJavaScript("arguments[0].scrollIntoView(true);", element);
    }


    protected void waitForPageLoad() {
        wait.until(webDriver ->
            ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    protected String getAttribute(WebElement element, String attribute) {
        waitForElementToBeVisible(element);
        return element.getAttribute(attribute);
    }


    protected boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    @Step("Accept alert")
    protected void acceptAlert() {
        try {
            // Try to wait for alert and accept it
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            logger.info("Alert accepted");
        } catch (Exception e) {
            // Alert might have been auto-accepted by browser configuration
            logger.info("Alert was auto-handled or not present: {}", e.getMessage());
        }
    }


    protected String getAlertText() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return driver.switchTo().alert().getText();
        } catch (Exception e) {
            logger.warn("Could not get alert text: {}", e.getMessage());
            return "";
        }
    }
}

