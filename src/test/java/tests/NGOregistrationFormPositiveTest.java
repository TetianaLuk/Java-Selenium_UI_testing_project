package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.Step;
import models.NGO;
import models.NGOforBuilderPattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import pageobjects.NGO_RegistrationPage;
import pageobjects.SkarbHomePage;
import utils.WebElementUtils;

import java.io.IOException;
import java.net.MalformedURLException;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

@ExtendWith(InstancioExtension.class)

public class NGOregistrationFormPositiveTest {

    private static final Logger logger = LogManager.getLogger(NGOtasksForVolunteerParameterizedTest.class);

    NGO_RegistrationPage obgNGO_RegistrationPage = new NGO_RegistrationPage (driver);

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
                .clickOnCreateNewNGO();
        logger.info("The driver is set up. Navigated to initial webpage.");
    }

    @Test
    @DisplayName("Checking the initial loading of the web page")
    @Description("This test verifying the initial webpage is loaded successfully. Fails if at least one web element is invisible or disable.")
    @Severity(CRITICAL)
    public void verifyInitialWebPageLoad() {
        assertAll("Checking the initial loading of the web page",
                () -> assertTrue(driver.getCurrentUrl().contains("registration/organizations"),"URL should contain text registration/volunteers"),
                () -> assertTrue(obgNGO_RegistrationPage.getFirstNameInput().isDisplayed(), "firstName field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getLastNameInput().isDisplayed(),"lastName field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getGenderField().isEnabled(),"gender field is disable"),
                () -> assertTrue(obgNGO_RegistrationPage.getEmailInput().isDisplayed(),"email field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getPhoneNumberInput().isDisplayed(),"phoneNumber field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getPasswordInput().isDisplayed(), "password field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getConfPasswordInput().isDisplayed(),"confirmPassword field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getOrganizationNameInput().isDisplayed(),"organizationName field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getSelectCategoryField().isEnabled(),"categoryIds field is disable"),
                () -> assertTrue(obgNGO_RegistrationPage.getPositionInput().isDisplayed(), "position field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getOrganizationLinkInput().isDisplayed(), "organizationLink field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getAboutInput().isDisplayed(),"about field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getOrganizationRegistrationUrlInput().isDisplayed(), "organizationRegistrationUrl field is invisible"),
                () -> assertTrue(obgNGO_RegistrationPage.getSubmitButton().isDisplayed(),"submit button is invisible")
        );
        logger.info("Initial WebPage is loaded successfully.");
    }

    @Test
    @DisplayName("Test with valid data input")
    @Description("This test attempts to register new NGO user using valid data.")
    @Severity(CRITICAL)
    public void validDataInput() {
        NGO user = Instancio.create(NGO.getNGO_Model());

        NGOforBuilderPattern NGOforBuilderPattern = new NGOforBuilderPattern.Builder()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setGender(user.getGender())
                .setEmail(user.getEmail())
                .setPhoneNumber(user.getPhoneNumber())
                .setPassword(user.getPassword())
                .setOrganizationName(user.getOrganizationName())
                .setSelectCategory(user.getSelectCategory())
                .setPosition(user.getPosition())
                .setOrganizationLink(user.getOrganizationLink())
                .setAbout(user.getAbout())
                .setRegisterLink(user.getRegisterLink()).build();

        logger.info("validDataInput test started. Entering user data.");

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(NGOforBuilderPattern.getGender())));
        obgNGO_RegistrationPage.populateNGOregistrationForm(NGOforBuilderPattern.getFirstName(), NGOforBuilderPattern.getLastName(), NGOforBuilderPattern.getEmail(),NGOforBuilderPattern.getPhoneNumber(),NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getOrganizationName(), NGOforBuilderPattern.getSelectCategory(), NGOforBuilderPattern.getPosition(), NGOforBuilderPattern.getOrganizationLink(), NGOforBuilderPattern.getAbout(), NGOforBuilderPattern.getRegisterLink());

        assertTrue(obgNGO_RegistrationPage.verifyNGOregistrationFormPopulatedCorrectly(NGOforBuilderPattern.getFirstName(), NGOforBuilderPattern.getLastName(), NGOforBuilderPattern.getEmail(), NGOforBuilderPattern.getPhoneNumber(), NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getOrganizationName(), NGOforBuilderPattern.getSelectCategory(), NGOforBuilderPattern.getPosition(), NGOforBuilderPattern.getOrganizationLink(), NGOforBuilderPattern.getAbout(), NGOforBuilderPattern.getRegisterLink()), "NGO registration form populated incorrectly");

        obgNGO_RegistrationPage.clickOnSubmitButton();

        obgNGO_RegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("Registration of user is successful. Message about successful registration is visible.");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
