package com.fintrack.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Warning: config.properties not found or unreadable.");
        }
    }

    public static String get(String key) {
        // Try environment variable first
        String value = System.getenv(key);
        if (value == null) {
            // Fallback to properties file
            value = properties.getProperty(key);
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }
}
