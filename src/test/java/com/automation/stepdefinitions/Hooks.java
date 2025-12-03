package com.automation.stepdefinitions;

import com.automation.driver.DriverManager;
import com.automation.utils.ConfigReader;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hooks - Cucumber hooks for setup and teardown
 */
public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("========================================");

        // Initialize driver
        DriverManager.initializeDriver();
        logger.info("Browser {} initialized", ConfigReader.getBrowser());
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Scenario status: {}", scenario.getStatus());

        // Take screenshot on failure
        if (scenario.isFailed()) {
            logger.error("Scenario failed: {}", scenario.getName());
            ScreenshotUtil.attachScreenshotToAllure(scenario.getName() + " - Failed");
        }

        // Quit driver
        DriverManager.quitDriver();
        logger.info("========================================");
        logger.info("Finished scenario: {}", scenario.getName());
        logger.info("========================================");
    }
}

