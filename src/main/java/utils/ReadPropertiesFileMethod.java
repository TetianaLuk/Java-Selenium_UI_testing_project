package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFileMethod {

    static Properties properties = new Properties();

    public static void readProperties(String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Properties file not found");
        }
    }

    public static String readHomePageUrlInConfigFile() throws IOException {
        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Properties file not found");
        }
        return properties.getProperty("URL_Skarb_Home_Page");
    }

    public static String readLMSHomePageUrlInConfigFile() throws IOException {
        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Properties file not found");
        }
        return properties.getProperty("URL_LMS_Home_Page");
    }

    public static Properties getProperties() {
        return properties;
    }
}
