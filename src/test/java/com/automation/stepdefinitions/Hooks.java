package com.automation.stepdefinitions;

import com.automation.driver.DriverManager;
import com.automation.utils.ConfigurationFileReader;
import com.automation.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("========================================");

        DriverManager.initializeDriver();
        logger.info("Browser {} initialized", ConfigurationFileReader.getBrowser());
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Scenario status: {}", scenario.getStatus());

        if (scenario.isFailed()) {
            logger.error("Scenario failed: {}", scenario.getName());
            ScreenshotUtil.attachScreenshotToAllure(scenario.getName() + " - Failed");
        }

        DriverManager.quitDriver();
        logger.info("========================================");
        logger.info("Finished scenario: {}", scenario.getName());
        logger.info("========================================");
    }
}

