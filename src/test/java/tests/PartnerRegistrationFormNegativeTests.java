package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.Step;
import models.Partner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import pageobjects.PartnerRegistrationPage;
import pageobjects.SkarbHomePage;
import utils.WebElementUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

@ExtendWith(InstancioExtension.class)

public class PartnerRegistrationFormNegativeTests {

    PartnerRegistrationPage objPartnerRegistrationPage = new PartnerRegistrationPage(driver);

    private static final Logger logger = LogManager.getLogger(PartnerRegistrationFormNegativeTests.class);

    Partner user = Instancio.create(Partner.getPartnerModel());

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
                                    .clickOnCreateNewPartner();
    }

    @Test
    @DisplayName("Test the form with mandatory fields left blank")
    @Description("This test checks the form behavior when trying to register new partner with mandatory fields left blank.")
    @Severity(CRITICAL)
    public void verifyFormWithMandatoryFieldsLeftBlank() {
        logger.info("verifyFormWithMandatoryFieldsLeftBlank test started. Entering user data.");

        objPartnerRegistrationPage.enterPhoneNumber(user.getPhoneNumber())
                                .selectCategory(user.getSelectCategory())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", objPartnerRegistrationPage.getSubmitButton());
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageUnderFirstNameField()
                                .verifyErrorMessageUnderLastNameField()
                                .verifyErrorMessageWhenSexFieldLeftBlank()
                                .verifyErrorMessageWhenEmailFieldLeftBlank()
                                .verifyErrorMessageWhenPasswordFieldLeftBlank()
                                .verifyErrorMessageWhenConfirmPasswordFieldLeftBlank()
                                .verifyErrorMessageUnderOrganizationNameField()
                                .verifyErrorMessageUnderPositionField();

        logger.info("verifyFormWithMandatoryFieldsLeftBlank test execution completed. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName, Gender, email, Password, confirmPassword, organizationName, organizationName fields are all visible.");
        logger.debug("verifyFormWithMandatoryFieldsLeftBlank test. User data entered. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName, Gender, email, Password, confirmPassword, organizationName, organizationName fields are all visible.");
    }


    @Test
    @DisplayName("Test text fields with symbol input")
    @Description("This test checks the form behavior when trying to register new partner with symbol input in text fields.")
    @Severity(CRITICAL)
    public void verifyTextFieldsWithSymbolInput() {
        logger.info("verifyTextFieldsWithSymbolInput test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName("David@#$%")
                                .enterLastName("Martinez@#$%")
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName("Green Horizon!!!")
                                .selectCategory(user.getSelectCategory())
                                .enterPosition("Outreach Coordinator*&^")
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout("Green Horizon works towards creating greener urban spaces!~{}")
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageUnderFirstNameField()
                                .verifyErrorMessageUnderLastNameField()
                                .verifyErrorMessageUnderOrganizationNameField()
                                .verifyErrorMessageUnderPositionField();

        logger.info("verifyTextFieldsWithSymbolInput test execution completed. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName, organizationName, organizationName fields are all visible.");
        logger.debug("verifyTextFieldsWithSymbolInput test. User data entered. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName, organizationName, organizationName fields are all visible.");
    }

    @Test
    @DisplayName("Test text fields case sensitivity")
    @Description("This test checks the case sensitivity of text fields.")
    @Severity(CRITICAL)
    public void verifyTextFieldsCaseSensitivity() {
        logger.info("verifyTextFieldsCaseSensitivity test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName("james")
                                .enterLastName("WILSON")
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName("EcoVision")
                                .selectCategory(user.getSelectCategory())
                                .enterPosition("PROJECT COORDINATOR")
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout("EcoVision strives to Empower local communities to take Action towards a more sustainable future through EduCation and ActiVism.")
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageUnderFirstNameField()
                                .verifyErrorMessageUnderLastNameField();

        logger.info("verifyTextFieldsCaseSensitivity test execution completed. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName fields are all visible.");
        logger.debug("verifyTextFieldsCaseSensitivity test. User data entered. Registration of user is failed as it was expected. Alert messages about incorrect filling of firstName, lastName fields are all visible.");
    }

    @Test
    @DisplayName("Test the form with email without @ sign")
    @Description("This test checks the form behavior when trying to register new partner with email without @ sign.")
    @Severity(CRITICAL)
    public void verifyFormWithEmailWithoutAtSign() throws IOException {
        logger.info("verifyFormWithEmailWithoutAtSign test started. Entering user data.");

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(properties.getProperty("email_8"))
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName(user.getOrganizationName())
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(user.getPosition())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageWhenEmailFieldWithInvalidInput();

        logger.info("verifyFormWithEmailWithoutAtSign test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of email field is visible.");
        logger.debug("verifyFormWithEmailWithoutAtSign test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of email field is visible.");
    }

    @Test
    @DisplayName("Test the form with short phone number")
    @Description("This test checks the form behavior when trying to register new partner with short phone number.")
    @Severity(CRITICAL)
    public void verifyFormWithShortPhoneNumber() {
        logger.info("verifyFormWithShortPhoneNumber test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber("+38067045678")
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName(user.getOrganizationName())
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(user.getPosition())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageWhenPhoneNumberFieldWithInvalidInput();

        logger.info("verifyFormWithShortPhoneNumber test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of phoneNumber field is visible.");
        logger.debug("verifyFormWithShortPhoneNumber test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of phoneNumber field is visible.");
    }

    @Test
    @DisplayName("Test the form with short password")
    @Description("This test checks the form behavior when trying to register new partner with short password.")
    @Severity(CRITICAL)
    public void verifyFormWithShortPassword() throws IOException {
        logger.info("verifyFormWithShortPassword test started. Entering user data.");

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(properties.getProperty("password_9"))
                                .enterConfirmPassword(properties.getProperty("confirmPassword_9"))
                                .enterOrganizationName(user.getOrganizationName())
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(user.getPosition())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", objPartnerRegistrationPage.getSubmitButton());
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageWhenPasswordFieldWithInvalidInput();

        logger.info("verifyFormWithShortPassword test execution completed. Registration of user is failed as it was expected. Alert message about incorrect filling of password field is visible.");
        logger.debug("verifyFormWithShortPassword test. User data entered. Registration of user is failed as it was expected. Alert message about incorrect filling of password field is visible.");
    }

    @Test
    @DisplayName("Test the form with invalid 'confirm password'")
    @Description("This test checks the form behavior when trying to register new partner with invalid data in 'confirm password' field.")
    @Severity(CRITICAL)
    public void verifyFormWithInvalidConfirmPassword() throws IOException {
        logger.info("verifyFormWithInvalidConfirmPassword test started. Entering user data.");

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(properties.getProperty("password_10"))
                                .enterConfirmPassword(properties.getProperty("confirmPassword_10"))
                                .enterOrganizationName(user.getOrganizationName())
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(user.getPosition())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", objPartnerRegistrationPage.getSubmitButton());
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        objPartnerRegistrationPage.verifyErrorMessageWhenConfirmPasswordFieldWithInvalidInput();

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
