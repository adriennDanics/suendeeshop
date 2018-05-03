package com.codecool.shop.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class loadConfigJDBC {
    private static Properties loadConfig() {
        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("src/main/resources/connection.properties");
            properties.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return properties;
    }
    public static Properties getProperties() {
        return loadConfig();
    }
}
