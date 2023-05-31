package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class WebConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    private static WebConfig instance;

    private final Properties properties;

    private WebConfig() {
        properties = new Properties();
        readConfig();
    }

    public static WebConfig getInstance() {
        if (instance == null) {
            instance = new WebConfig();
        }
        return instance;
    }

    public void readConfig() {
        try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        debugProperties();
    }

    private void debugProperties() {
        logger.info(" ╭ ⁀ ⁀ ╮");
        logger.info("( '\uD83D\uDC45'  ) [Read config from \"application.properties\"]");
        logger.info(" ╰ ‿  ‿ ╯");
        properties.forEach((key, value) -> logger.info("▶ Read application : {}={}", key, value));
    }

    public int getDefaultPort() {
        return Integer.parseInt(properties.getProperty("default_port"));
    }

    public String getTemplatesResourcePath() {
        return properties.getProperty("resource_path_templates");
    }

    public String getStaticResourcePath() {
        return properties.getProperty("resource_path_static");
    }
}
