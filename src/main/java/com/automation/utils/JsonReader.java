package com.automation.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;

/**
 * JsonReader - Utility class to read data from JSON files
 */
public class JsonReader {

    private static final Logger logger = LogManager.getLogger(JsonReader.class);
    private static JsonObject jsonObject;

    /**
     * Load JSON file
     */
    public static void loadJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            jsonObject = gson.fromJson(reader, JsonObject.class);
            logger.info("JSON file loaded successfully: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load JSON file: {}", filePath, e);
            throw new RuntimeException("Failed to load JSON file: " + filePath, e);
        }
    }

    /**
     * Get JSON object
     */
    public static JsonObject getJsonObject() {
        if (jsonObject == null) {
            loadJsonFile(ConfigReader.getProperty("json.path"));
        }
        return jsonObject;
    }

    /**
     * Get string value from JSON
     */
    public static String getString(String key) {
        return getJsonObject().get(key).getAsString();
    }

    /**
     * Get JSON object by key
     */
    public static JsonObject getObject(String key) {
        return getJsonObject().getAsJsonObject(key);
    }
}

