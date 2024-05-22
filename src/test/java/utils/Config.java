package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config() {
        FileInputStream input;
        properties = new Properties();
        try {
            input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getApiKey() {
        return properties.getProperty("api.key");
    }

    public String getBaseUrl() {
        return properties.getProperty("api.BASE_URL");
    }
}
