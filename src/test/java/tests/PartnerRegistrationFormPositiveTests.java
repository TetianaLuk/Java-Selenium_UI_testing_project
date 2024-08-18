package tests;

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
import org.openqa.selenium.By;
import pageobjects.PartnerRegistrationPage;
import pageobjects.SkarbHomePage;
import utils.WebElementUtils;

import java.io.IOException;
import java.net.MalformedURLException;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

@ExtendWith(InstancioExtension.class)

public class PartnerRegistrationFormPositiveTests {

    PartnerRegistrationPage objPartnerRegistrationPage = new PartnerRegistrationPage(driver);

    private static final Logger logger = LogManager.getLogger(PartnerRegistrationFormPositiveTests.class);

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
    @DisplayName("Checking the initial loading of the web page")
    @Description("This test verifying the initial webpage is loaded successfully. Fails if at least one web element is invisible or disable.")
    @Severity(CRITICAL)
    public void verifyInitialWebPageLoad() {
        assertAll("Checking the initial loading of the web page",
                () -> assertTrue(driver.getCurrentUrl().contains("registration/partners"),"URL should contain text registration/volunteers"),
                () -> assertTrue(objPartnerRegistrationPage.getFirstNameInput().isDisplayed(), "firstName field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getLastNameInput().isDisplayed(),"lastName field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getGenderField().isEnabled(),"gender field is disable"),
                () -> assertTrue(objPartnerRegistrationPage.getEmailInput().isDisplayed(),"email field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getPhoneNumberInput().isDisplayed(),"phoneNumber field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getPasswordInput().isDisplayed(), "password field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getConfPasswordInput().isDisplayed(),"confirmPassword field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getOrganizationNameInput().isDisplayed(),"organizationName field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getSelectCategoryField().isEnabled(),"categoryIds field is disable"),
                () -> assertTrue(objPartnerRegistrationPage.getPositionInput().isDisplayed(), "position field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getOrganizationLinkInput().isDisplayed(), "organizationLink field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getAboutInput().isDisplayed(),"about field is invisible"),
                () -> assertTrue(objPartnerRegistrationPage.getSubmitButton().isDisplayed(),"submit button is invisible")
        );
        logger.debug("verifyInitialWebPageLoad test. Initial WebPage is loaded successfully.");
        logger.info("verifyInitialWebPageLoad test. Initial WebPage is loaded successfully.");
    }

    @Test
    @DisplayName("Test with valid data input")
    @Description("This test attempts to register new partner user using valid data.")
    @Severity(CRITICAL)
    public void validDataInput() {
        Partner user = Instancio.create(Partner.getPartnerModel());

        logger.info("validDataInput test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName(user.getOrganizationName())
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(user.getPosition())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        objPartnerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("validDataInput test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("validDataInput test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }

    @Test
    @DisplayName("Test with only mandatory fields input")
    @Description("This test attempts to register new partner user filling up only mandatory fields of the form.")
    @Severity(CRITICAL)
    public void onlyMandatoryFieldsInput() {
        Partner user = Instancio.create(Partner.getPartnerModel());

        logger.info("onlyMandatoryFieldsInput test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName(user.getOrganizationName())
                                .enterPosition(user.getPosition())
                                .clickOnSubmitButton();

        objPartnerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("onlyMandatoryFieldsInput test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("onlyMandatoryFieldsInput test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }

    @Test
    @DisplayName("Test with Cyrillic data input")
    @Description("This test attempts to register new partner user filling up text fields with Cyrillic data.")
    @Severity(CRITICAL)
    public void cyrillicDataInput() {
        Partner user = Instancio.create(Partner.getPartnerModel());

        logger.info("cyrillicDataInput test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName("A"+utils.RandomDataGenerationMethods.generateRandomCyrillicString(5))
                                .enterLastName("A"+utils.RandomDataGenerationMethods.generateRandomCyrillicString(5))
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber(user.getPhoneNumber())
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName(utils.RandomDataGenerationMethods.generateRandomCyrillicString(5))
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(utils.RandomDataGenerationMethods.generateRandomCyrillicString(7))
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(utils.RandomDataGenerationMethods.generateRandomCyrillicString(7))
                                .clickOnSubmitButton();

        objPartnerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("cyrillicDataInput test execution completed. Registration of user is successful. Message about successful registration is visible.");
        logger.debug("cyrillicDataInput test. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

    }


    @Test
    @DisplayName("Test with phone number without code 38")
    @Description("This test attempts to register new partner user with phone number without code 38.")
    @Severity(CRITICAL)
    public void phoneWithoutCode38Input() {
        Partner user = Instancio.create(Partner.getPartnerModel());

        logger.info("phoneWithoutCode38Input test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                .enterLastName(user.getLastName())
                                .enterEmail(user.getEmail())
                                .enterPhoneNumber("0965676565")
                                .enterPassword(user.getPassword())
                                .enterConfirmPassword(user.getPassword())
                                .enterOrganizationName(user.getOrganizationName())
                                .selectCategory(user.getSelectCategory())
                                .enterPosition(user.getPosition())
                                .enterOrganizationLink(user.getOrganizationLink())
                                .enterAbout(user.getAbout())
                                .clickOnSubmitButton();

        objPartnerRegistrationPage.verifySuccessfulRegistrationMassage();
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
