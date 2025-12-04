package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseReader {

    private static final Logger logger = LogManager.getLogger(DatabaseReader.class);
    private static Connection connection;

    private static final String DB_URL = ConfigurationFileReader.getProperty("db.url");
    private static final String DB_USER = ConfigurationFileReader.getProperty("db.username");
    private static final String DB_PASSWORD = ConfigurationFileReader.getProperty("db.password");


    private static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                logger.info("Database connection established successfully");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found", e);
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            logger.error("Failed to connect to database: {}", e.getMessage());
            throw new RuntimeException("Failed to connect to database", e);
        }
    }


    public static Map<String, String> getUserCredentials(String email) {
        Map<String, String> credentials = new HashMap<>();
        String query = "SELECT email, password FROM users WHERE email = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                credentials.put("email", resultSet.getString("email"));
                credentials.put("password", resultSet.getString("password"));
                logger.info("Retrieved credentials for email: {}", email);
            } else {
                logger.warn("No credentials found for email: {}", email);
            }
        } catch (SQLException e) {
            logger.error("Error reading credentials from database: {}", e.getMessage());
            throw new RuntimeException("Failed to read credentials from database", e);
        }

        return credentials;
    }


    public static Map<String, Map<String, String>> getAllUserCredentials() {
        Map<String, Map<String, String>> allCredentials = new HashMap<>();
        String query = "SELECT email, password FROM users";

        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                Map<String, String> credentials = new HashMap<>();
                credentials.put("email", email);
                credentials.put("password", resultSet.getString("password"));
                allCredentials.put(email, credentials);
            }

            logger.info("Retrieved {} user credentials from database", allCredentials.size());
        } catch (SQLException e) {
            logger.error("Error reading all credentials from database: {}", e.getMessage());
            throw new RuntimeException("Failed to read credentials from database", e);
        }

        return allCredentials;
    }

    public static void insertUserCredentials(String email, String password) {
        String query = "INSERT INTO users (email, password) VALUES (?, ?) " +
                      "ON DUPLICATE KEY UPDATE password = ?";

        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, password);
            int rows = statement.executeUpdate();
            logger.info("Inserted/Updated {} row(s) for email: {}", rows, email);
        } catch (SQLException e) {
            logger.error("Error inserting credentials into database: {}", e.getMessage());
            throw new RuntimeException("Failed to insert credentials into database", e);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed");
            }
        } catch (SQLException e) {
            logger.error("Error closing database connection: {}", e.getMessage());
        }
    }
}

