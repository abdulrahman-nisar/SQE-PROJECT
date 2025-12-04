package com.automation.driver;

import com.automation.utils.ConfigurationFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initializeDriver() {
        String browser = ConfigurationFileReader.getBrowser().toLowerCase();
        logger.info("Initializing {} driver", browser);

        WebDriver webDriver = null;

        switch (browser) {
            case "chrome":
                webDriver = createChromeDriver();
                break;
            case "firefox":
                webDriver = createFirefoxDriver();
                break;
            case "edge":
                webDriver = createEdgeDriver();
                break;
            default:
                logger.error("Unsupported browser: {}", browser);
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.set(webDriver);
        configureDriver();
        logger.info("Driver initialized successfully");
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (ConfigurationFileReader.isHeadless()) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");

        // Configure alert handling - accept alerts instead of dismissing them
        options.setCapability("unhandledPromptBehavior", "accept");

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (ConfigurationFileReader.isHeadless()) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (ConfigurationFileReader.isHeadless()) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    private static void configureDriver() {
        WebDriver webDriver = driver.get();

        webDriver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(ConfigurationFileReader.getImplicitWait())
        );

        webDriver.manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(ConfigurationFileReader.getPageLoadTimeout())
        );

        if (ConfigurationFileReader.shouldMaximize()) {
            webDriver.manage().window().maximize();
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting driver");
            driver.get().quit();
            driver.remove();
        }
    }
}

