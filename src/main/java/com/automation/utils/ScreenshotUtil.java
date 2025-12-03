package com.automation.utils;

import com.automation.driver.DriverManager;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil - Utility class for taking screenshots
 */
public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "screenshots/";

    /**
     * Take screenshot and save to file
     */
    public static String takeScreenshot(String fileName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logger.warn("Driver is null, cannot take screenshot");
            return null;
        }

        try {
            // Create screenshots directory if not exists
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            // Take screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Generate file name with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotPath = SCREENSHOT_DIR + fileName + "_" + timestamp + ".png";
            File destinationFile = new File(screenshotPath);

            // Copy screenshot to destination
            FileUtils.copyFile(sourceFile, destinationFile);

            logger.info("Screenshot saved: {}", screenshotPath);
            return screenshotPath;

        } catch (IOException e) {
            logger.error("Failed to take screenshot", e);
            return null;
        }
    }

    /**
     * Take screenshot and attach to Allure report
     */
    public static void attachScreenshotToAllure(String screenshotName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logger.warn("Driver is null, cannot attach screenshot to Allure");
            return;
        }

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(screenshotName, "image/png",
                new ByteArrayInputStream(screenshotBytes), "png");

            logger.info("Screenshot attached to Allure: {}", screenshotName);

        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Allure", e);
        }
    }

    /**
     * Take screenshot on test failure
     */
    public static void captureScreenshotOnFailure(String testName) {
        if (ConfigReader.isScreenshotOnFailure()) {
            takeScreenshot(testName + "_FAILED");
            attachScreenshotToAllure(testName + " - Failure Screenshot");
        }
    }
}

