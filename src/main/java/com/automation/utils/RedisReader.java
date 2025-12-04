package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisReader {

    private static final Logger logger = LogManager.getLogger(RedisReader.class);
    private static JedisPool jedisPool;

    private static final String REDIS_HOST = ConfigurationFileReader.getProperty("redis.host");
    private static final int REDIS_PORT = Integer.parseInt(ConfigurationFileReader.getProperty("redis.port"));
    private static final int REDIS_TIMEOUT = Integer.parseInt(ConfigurationFileReader.getProperty("redis.timeout"));

    private static final String USER_KEY_PREFIX = "user:";

    private static JedisPool getJedisPool() {
        if (jedisPool == null) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);

            jedisPool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT, REDIS_TIMEOUT);
            logger.info("Redis connection pool initialized successfully");
        }
        return jedisPool;
    }

    public static Map<String, String> getUserCredentialsFromRedis(String email) {
        Map<String, String> credentials = new HashMap<>();
        String key = USER_KEY_PREFIX + email;

        try (Jedis jedis = getJedisPool().getResource()) {
            Map<String, String> userData = jedis.hgetAll(key);

            if (userData != null && !userData.isEmpty()) {
                credentials.put("email", email);
                credentials.put("password", userData.get("password"));
                logger.info("Retrieved credentials from Redis for email: {}", email);
            } else {
                logger.warn("No credentials found in Redis for email: {}", email);
            }
        } catch (Exception e) {
            logger.error("Error reading credentials from Redis: {}", e.getMessage());
            throw new RuntimeException("Failed to read credentials from Redis", e);
        }

        return credentials;
    }

    public static Map<String, Map<String, String>> getAllUserCredentialsFromRedis() {
        Map<String, Map<String, String>> allCredentials = new HashMap<>();

        try (Jedis jedis = getJedisPool().getResource()) {
            Set<String> keys = jedis.keys(USER_KEY_PREFIX + "*");

            for (String key : keys) {
                String email = key.substring(USER_KEY_PREFIX.length());
                Map<String, String> userData = jedis.hgetAll(key);

                if (userData != null && !userData.isEmpty()) {
                    Map<String, String> credentials = new HashMap<>();
                    credentials.put("email", email);
                    credentials.put("password", userData.get("password"));
                    allCredentials.put(email, credentials);
                }
            }

            logger.info("Retrieved {} user credentials from Redis", allCredentials.size());
        } catch (Exception e) {
            logger.error("Error reading all credentials from Redis: {}", e.getMessage());
            throw new RuntimeException("Failed to read credentials from Redis", e);
        }

        return allCredentials;
    }

    public static void setUserCredentialsInRedis(String email, String password) {
        String key = USER_KEY_PREFIX + email;

        try (Jedis jedis = getJedisPool().getResource()) {
            Map<String, String> userData = new HashMap<>();
            userData.put("password", password);

            jedis.hset(key, userData);
            logger.info("Stored credentials in Redis for email: {}", email);
        } catch (Exception e) {
            logger.error("Error storing credentials in Redis: {}", e.getMessage());
            throw new RuntimeException("Failed to store credentials in Redis", e);
        }
    }

    public static void deleteUserCredentialsFromRedis(String email) {
        String key = USER_KEY_PREFIX + email;

        try (Jedis jedis = getJedisPool().getResource()) {
            jedis.del(key);
            logger.info("Deleted credentials from Redis for email: {}", email);
        } catch (Exception e) {
            logger.error("Error deleting credentials from Redis: {}", e.getMessage());
        }
    }

    public static boolean userExistsInRedis(String email) {
        String key = USER_KEY_PREFIX + email;

        try (Jedis jedis = getJedisPool().getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error("Error checking user existence in Redis: {}", e.getMessage());
            return false;
        }
    }

    public static void clearAllUserCredentials() {
        try (Jedis jedis = getJedisPool().getResource()) {
            Set<String> keys = jedis.keys(USER_KEY_PREFIX + "*");
            if (!keys.isEmpty()) {
                jedis.del(keys.toArray(new String[0]));
                logger.info("Cleared {} user credentials from Redis", keys.size());
            }
        } catch (Exception e) {
            logger.error("Error clearing credentials from Redis: {}", e.getMessage());
        }
    }

    public static void closeRedisPool() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            logger.info("Redis connection pool closed");
        }
    }
}

