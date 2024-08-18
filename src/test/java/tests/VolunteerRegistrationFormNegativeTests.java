package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import models.Volunteer;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pageobjects.SkarbHomePage;
import pageobjects.VolunteerRegistrationPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;
import io.qameta.allure.Step;

@ExtendWith(InstancioExtension.class)

public class VolunteerRegistrationFormNegativeTests {

    private static final Logger logger = LogManager.getLogger(VolunteerRegistrationFormNegativeTests.class);

    VolunteerRegistrationPage objVolunteerRegistrationPage = new VolunteerRegistrationPage(driver);

    Volunteer user = Instancio.create(Volunteer.getUserModel());

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        chromeSetUp();
    }

    @BeforeEach
    @Step("Navigating to initial webpage")
    public void navigateToInitialWebPage() throws IOException {
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        (new SkarbHomePage(driver)).clickOnSelectWebSiteLanguage()
                .clickOnSelectUkrLanguage()
                .clickOnCreateNewUser()
                .clickOnCreateNewVolunteer();
    }

    @Test
    @DisplayName("Test the form with mandatory fields left blank")
    @Description("This test checks the form behavior when trying to register new volunteer with mandatory fields left blank.")
    @Severity(CRITICAL)
    public void verifyFormWithMandatoryFieldsLeftBlank() {

        logger.info("verifyFormWithMandatoryFieldsLeftBlank test started. Entering user data.");

        objVolunteerRegistrationPage.enterPhoneNumber(user.getPhoneNumber())
                               .enterAbout(user.getAbout())
                               .selectCategory(user.getSelectCategory())
                               .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenFirstNameFieldLeftBlank()
                                    .verifyErrorMessageWhenLastNameFieldLeftBlank()
                                    .verifyErrorMessageWhenEmailFieldLeftBlank()
                                    .verifyErrorMessageWhenPasswordFieldLeftBlank()
                                    .verifyErrorMessageWhenConfirmPasswordFieldLeftBlank();

        logger.info("verifyFormWithMandatoryFieldsLeftBlank test execution completed. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName, email, Password, confirmPassword fields are all visible.");
        logger.debug("verifyFormWithMandatoryFieldsLeftBlank test. User data entered. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName, email, Password, confirmPassword fields are all visible.");
    }

    @Test
    @DisplayName("Test text fields with symbol input")
    @Description("This test checks the form behavior when trying to register new volunteer with symbol input in text fields.")
    @Severity(CRITICAL)
    public void verifyTextFieldsWithSymbolInput() {
        logger.info("verifyTextFieldsWithSymbolInput test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName("Michael@#$%")
                                .enterLastName("Wilson123")
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout("Committed to making a difference and contributing to positive change.!~{}")
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenFirstNameFieldWithInvalidInput()
                                    .verifyErrorMessageWhenLastNameFieldWithInvalidInput();

        logger.info("verifyTextFieldsWithSymbolInput test execution completed. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName fields are all visible.");
        logger.debug("verifyTextFieldsWithSymbolInput test. User data entered. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName fields are all visible.");
    }

    @Test
    @DisplayName("Test text fields case sensitivity")
    @Description("This test checks the case sensitivity of text fields.")
    @Severity(CRITICAL)
    public void verifyTextFieldsCaseSensitivity() {
        logger.info("verifyTextFieldsCaseSensitivity test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName("matthew")
                                .enterLastName("RODRIGUEZ")
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout("CommiTted to VoluNteer work and Helping those in need.")
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenLastNameFieldWithInvalidInput();

        logger.info("verifyTextFieldsCaseSensitivity test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of lastName field is visible.");
        logger.debug("verifyTextFieldsCaseSensitivity test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of lastName field is visible.");
    }

    @Test
    @DisplayName("Test the form with email without @ sign")
    @Description("This test checks the form behavior when trying to register new volunteer with email without @ sign.")
    @Severity(CRITICAL)
    public void verifyFormWithEmailWithoutAtSign() throws IOException {
        logger.info("verifyFormWithEmailWithoutAtSign test started. Entering user data.");

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(properties.getProperty("email_5"))
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout(user.getAbout())
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenEmailFieldWithInvalidInput();

        logger.info("verifyFormWithEmailWithoutAtSign test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of email field is visible.");
        logger.debug("verifyFormWithEmailWithoutAtSign test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of email field is visible.");
    }

    @Test
    @DisplayName("Test the form with short phone number")
    @Description("This test checks the form behavior when trying to register new volunteer with short phone number.")
    @Severity(CRITICAL)
    public void verifyFormWithShortPhoneNumber() {
        logger.info("verifyFormWithShortPhoneNumber test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber("+38093890123")
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout(user.getAbout())
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenPhoneNumberFieldWithInvalidInput();

        logger.info("verifyFormWithShortPhoneNumber test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of phoneNumber field is visible.");
        logger.debug("verifyFormWithShortPhoneNumber test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of phoneNumber field is visible.");
    }

    @Test
    @DisplayName("Test the form with short password")
    @Description("This test checks the form behavior when trying to register new volunteer with short password.")
    @Severity(CRITICAL)
    public void verifyFormWithShortPassword() throws IOException {
        logger.info("verifyFormWithShortPassword test started. Entering user data.");

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(properties.getProperty("password_6"))
                                .enterConfirmPassword(properties.getProperty("confirmPassword_6"))
                                .enterAbout(user.getAbout())
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenPasswordFieldWithInvalidInput();

        logger.info("verifyFormWithShortPassword test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of password field is visible.");
        logger.debug("verifyFormWithShortPassword test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of password field is visible.");
    }

    @Test
    @DisplayName("Test the form with invalid 'confirm password'")
    @Description("This test checks the form behavior when trying to register new volunteer with invalid data in 'confirm password' field.")
    @Severity(CRITICAL)
    public void verifyFormWithInvalidConfirmPassword() throws IOException {
        logger.info("verifyFormWithInvalidConfirmPassword test started. Entering user data.");

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(properties.getProperty("password_7"))
                                .enterConfirmPassword(properties.getProperty("confirmPassword_7"))
                                .enterAbout(user.getAbout())
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objVolunteerRegistrationPage.verifyErrorMessageWhenConfirmPasswordFieldWithInvalidInput();

        logger.info("verifyFormWithInvalidConfirmPassword test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of confirmPassword field is visible.");
        logger.debug("verifyFormWithInvalidConfirmPassword test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of confirmPassword field is visible.");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
