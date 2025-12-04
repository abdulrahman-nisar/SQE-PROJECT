package com.automation.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;


public class JsonReader {

    private static final Logger logger = LogManager.getLogger(JsonReader.class);
    private static JsonObject jsonObject;

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

    public static JsonObject getJsonObject() {
        if (jsonObject == null) {
            loadJsonFile(ConfigurationFileReader.getProperty("json.path"));
        }
        return jsonObject;
    }

    public static String getString(String key) {
        return getJsonObject().get(key).getAsString();
    }

    public static JsonObject getObject(String key) {
        return getJsonObject().getAsJsonObject(key);
    }
}

