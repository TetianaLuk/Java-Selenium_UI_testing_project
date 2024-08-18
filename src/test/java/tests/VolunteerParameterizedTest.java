package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.Step;
import models.Volunteer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pageobjects.SkarbHomePage;
import pageobjects.VolunteerRegistrationPage;
import java.io.IOException;
import java.net.MalformedURLException;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

@ExtendWith(InstancioExtension.class)

public class VolunteerParameterizedTest {

    VolunteerRegistrationPage objVolunteerRegistrationPage = new VolunteerRegistrationPage(driver);
    private static final Logger logger = LogManager.getLogger(VolunteerParameterizedTest.class);

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

    @ParameterizedTest
    @MethodSource("testdata.VolunteerParameterizedTestData#getVolunteerParameterizedTestData")
    @DisplayName("Create new volunteer with parameterized test and @MethodSource")
    @Description("This test attempts to register new volunteer user using valid data from @MethodSource.")
    @Severity(CRITICAL)
    public void createVolunteerTestUser(Volunteer user) {
        logger.info("createVolunteerTestUser test started. Entering user data.");

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
        logger.info("createVolunteerTestUser test execution completed. Registration of user is successful. Message about successful registration is visible.");

    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
