package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationFileReader {

    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config file");
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static boolean shouldMaximize() {
        return Boolean.parseBoolean(getProperty("maximize", "true"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public static String getAppUrl() {
        return getProperty("app.url");
    }

    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure", "true"));
    }

    public static String getExcelPath() {
        return getProperty("excel.path");
    }

    public static String getDatabaseUrl() {
        return getProperty("db.url");
    }

    public static String getDatabaseUsername() {
        return getProperty("db.username");
    }

    public static String getDatabasePassword() {
        return getProperty("db.password");
    }

    public static String getRedisHost() {
        return getProperty("redis.host", "localhost");
    }

    public static int getRedisPort() {
        return Integer.parseInt(getProperty("redis.port", "6379"));
    }

    public static int getPageLoadWait() {
        return Integer.parseInt(getProperty("test.speed.page.load", "3000"));
    }

    public static int getElementActionWait() {
        return Integer.parseInt(getProperty("test.speed.element.action", "1000"));
    }

    public static void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Wait interrupted", e);
        }
    }

    public static void shortWait() {
        waitFor(getShortWait());
    }
    public static int getShortWait() {
        return Integer.parseInt(getProperty("test.speed.short.wait", "2000"));
    }

    public static void mediumWait() {
        waitFor(getMediumWait());
    }
    public static int getMediumWait() {
        return Integer.parseInt(getProperty("test.speed.medium.wait", "3000"));
    }

    public static void longWait() {
        waitFor(getLongWait());
    }
    public static int getLongWait() {
        return Integer.parseInt(getProperty("test.speed.long.wait", "5000"));
    }

}

