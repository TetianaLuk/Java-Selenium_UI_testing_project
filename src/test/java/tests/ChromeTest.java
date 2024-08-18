package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChromeTest {

    private static WebDriver driver;
    private static Properties properties;

    @BeforeAll
    public static void setUp() throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/testdata/config.properties")) {
            properties.load(fis);
        }
    }

    @Test
    @DisplayName("Open Chrome browser and go to specified url")
    @Description("This test attempts to open Chrome browser and go to specified url.")
    @Severity(CRITICAL)
    public void testChrome() {
        String url = properties.getProperty("URL");
        driver = new ChromeDriver();
        driver.get(url);
        assertTrue(driver.getCurrentUrl().contains("login"));
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

}
