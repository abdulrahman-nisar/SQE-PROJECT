package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseReader {

    private static final Logger logger = LogManager.getLogger(DatabaseReader.class);
    private static Connection connection;

    public static void connect() {
        try {
            String url = ConfigurationFileReader.getDatabaseUrl();
            String username = ConfigurationFileReader.getDatabaseUsername();
            String password = ConfigurationFileReader.getDatabasePassword();

            connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connection established");

        } catch (SQLException e) {
            logger.error("Failed to connect to database", e);
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection", e);
            }
        }
    }

    public static List<Map<String, String>> executeQuery(String query) {
        List<Map<String, String>> results = new ArrayList<>();

        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = resultSet.getString(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

            resultSet.close();
            statement.close();

            logger.info("Query executed successfully. Rows returned: {}", results.size());

        } catch (SQLException e) {
            logger.error("Error executing query: {}", query, e);
            throw new RuntimeException("Failed to execute query", e);
        }

        return results;
    }

    public static int executeUpdate(String query) {
        int rowsAffected = 0;

        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }

            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
            statement.close();

            logger.info("Update query executed. Rows affected: {}", rowsAffected);

        } catch (SQLException e) {
            logger.error("Error executing update query: {}", query, e);
            throw new RuntimeException("Failed to execute update query", e);
        }

        return rowsAffected;
    }

    public static String getSingleValue(String query) {
        List<Map<String, String>> results = executeQuery(query);
        if (!results.isEmpty()) {
            Map<String, String> firstRow = results.get(0);
            return firstRow.values().iterator().next();
        }
        return null;
    }

    public static boolean isRecordExists(String query) {
        List<Map<String, String>> results = executeQuery(query);
        return !results.isEmpty();
    }
}

