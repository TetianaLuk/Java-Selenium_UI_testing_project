package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import models.Volunteer;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pageobjects.SkarbHomePage;
import pageobjects.VolunteerRegistrationPage;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;
import static io.qameta.allure.SeverityLevel.*;
import io.qameta.allure.Step;

@ExtendWith(InstancioExtension.class)

public class VolunteerRegistrationFormPositiveTests {

    private static final Logger logger = LogManager.getLogger(VolunteerRegistrationFormPositiveTests.class);

    VolunteerRegistrationPage objVolunteerRegistrationPage = new VolunteerRegistrationPage(driver);

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
    @DisplayName("Checking the initial loading of the web page")
    @Description("This test verifying the initial webpage is loaded successfully. Fails if at least one web element is invisible or disable.")
    @Severity(CRITICAL)
    public void verifyInitialWebPageLoad() {
        assertAll("Checking the initial loading of the web page",
                () -> assertTrue(driver.getCurrentUrl().contains("registration/volunteers"),"URL should contain text registration/volunteers"),
                () -> assertTrue(objVolunteerRegistrationPage.getFirstNameInput().isDisplayed(), "firstName field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getLastNameInput().isDisplayed(),"lastName field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getEmailInput().isDisplayed(),"email field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getPhoneNumberInput().isDisplayed(),"phoneNumber field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getPasswordInput().isDisplayed(), "password field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getConfPasswordInput().isDisplayed(),"confirmPassword field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getAboutInput().isDisplayed(),"about field is invisible"),
                () -> assertTrue(objVolunteerRegistrationPage.getSelectCategoryField().isEnabled(),"categoryIds field is disable"),
                () -> assertTrue(objVolunteerRegistrationPage.getSubmitButton().isDisplayed(),"submit button is invisible")
        );
        logger.debug("verifyInitialWebPageLoad test. Initial WebPage is loaded successfully.");
        logger.info("verifyInitialWebPageLoad test. Initial WebPage is loaded successfully.");

    }

    @Test
    @DisplayName("Test with valid data input")
    @Description("This test attempts to register new volunteer user using valid data.")
    @Severity(CRITICAL)
    public void validDataInput() {
        Volunteer user = Instancio.create(Volunteer.getUserModel());

        logger.info("validDataInput test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout(user.getAbout())
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        objVolunteerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("validDataInput test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("validDataInput test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }

    @Test
    @DisplayName("Test with only mandatory fields input")
    @Description("This test attempts to register new volunteer user filling up only mandatory fields of the form.")
    @Severity(CRITICAL)
    public void onlyMandatoryFieldsInput() {
        Volunteer user = Instancio.create(Volunteer.getUserModel());

        logger.info("onlyMandatoryFieldsInput test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .clickOnSubmitButton();

        objVolunteerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("onlyMandatoryFieldsInput test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("onlyMandatoryFieldsInput test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }

    @Test
    @DisplayName("Test with Cyrillic data input")
    @Description("This test attempts to register new volunteer user filling up text fields with Cyrillic data.")
    @Severity(CRITICAL)
    public void cyrillicDataInput() {
        Volunteer user = Instancio.create(Volunteer.getUserModel());

        logger.info("cyrillicDataInput test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName("Роман")
                                .enterLastName("Ивасюк")
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout("Люблю волонтерить у свободное от работы время.")
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        objVolunteerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("cyrillicDataInput test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("cyrillicDataInput test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }

    @Test
    @DisplayName("Test with phone number without code 38")
    @Description("This test attempts to register new volunteer user with phone number without code 38.")
    @Severity(CRITICAL)
    public void phoneWithoutCode38Input() {
        Volunteer user = Instancio.create(Volunteer.getUserModel());

        logger.info("phoneWithoutCode38Input test started. Entering user data.");

        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber("0968888888")
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterAbout(user.getAbout())
                                .selectCategory(user.getSelectCategory())
                                .clickOnSubmitButton();

        objVolunteerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("phoneWithoutCode38Input test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("phoneWithoutCode38Input test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}