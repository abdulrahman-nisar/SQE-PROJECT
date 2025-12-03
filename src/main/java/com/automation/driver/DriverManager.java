package com.automation.driver;

import com.automation.utils.ConfigReader;
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

/**
 * DriverManager - Manages WebDriver instances using ThreadLocal for parallel execution
 */
public class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Get WebDriver instance for current thread
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Initialize WebDriver based on browser configuration
     */
    public static void initializeDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
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

    /**
     * Create Chrome WebDriver
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (ConfigReader.isHeadless()) {
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

    /**
     * Create Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }

        return new FirefoxDriver(options);
    }

    /**
     * Create Edge WebDriver
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }

        return new EdgeDriver(options);
    }

    /**
     * Configure WebDriver with timeouts and window settings
     */
    private static void configureDriver() {
        WebDriver webDriver = driver.get();

        webDriver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(ConfigReader.getImplicitWait())
        );

        webDriver.manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(ConfigReader.getPageLoadTimeout())
        );

        if (ConfigReader.shouldMaximize()) {
            webDriver.manage().window().maximize();
        }
    }

    /**
     * Quit WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting driver");
            driver.get().quit();
            driver.remove();
        }
    }
}

