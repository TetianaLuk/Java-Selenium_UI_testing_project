package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import models.Partner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.MailHogPage;
import pageobjects.PartnerRegistrationPage;
import pageobjects.SkarbHomePage;
import utils.WebElementUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

@ExtendWith(InstancioExtension.class)

public class PartnerInMailHogServiceTest {

    private static final Logger logger = LogManager.getLogger(PartnerInMailHogServiceTest.class);

    MailHogPage objMailHogPage = new MailHogPage(driver);
    PartnerRegistrationPage objPartnerRegistrationPage = new PartnerRegistrationPage(driver);

    @BeforeAll
    public static void setUpAndNavigateToInitialWebPage() throws IOException {
        chromeSetUp();
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        (new SkarbHomePage(driver)).clickOnSelectWebSiteLanguage()
                .clickOnSelectUkrLanguage()
                .clickOnCreateNewUser()
                .clickOnCreateNewPartner();
    }

    @Test
    @DisplayName("Confirm partner registration in MailHog service")
    @Description("This test attempts to register new partner and then confirm his email in the MailHog service.")
    @Severity(CRITICAL)
    public void confirmPartnerRegistrationInMailHogService() throws IOException {
        Partner user = Instancio.create(Partner.getPartnerModel());

        logger.info("confirmPartnerRegistrationInMailHogService test started. Entering user data.");

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
        logger.info("Registration of user is successful. Message about successful registration is visible.");
        logger.debug("confirmPartnerRegistrationInMailHogService test started. User data entered. Registration of test user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(objMailHogPage.getMailHogURL());
        objMailHogPage.checkIfSuccessfullyNavigatedToMailHogWebpage();
        logger.info("A new tab created. Navigated to the MailHog website.");
        logger.debug("A new tab created. Navigated to the MailHog website.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + user.getEmail() + "')]")));

        objMailHogPage.verifyLetterAboutSuccessfulRegistrationIsPresentOnWebPage(user.getEmail());
        logger.info("Letter about successful registration is present in the MailHog.");
        logger.debug("Letter about successful registration of email {} is present in the MailHog.", user.getEmail());

        WebElement letter = driver.findElement(By.xpath("//div[contains(text(),'" + user.getEmail() + "')]"));
        letter.click();

        assertTrue(objMailHogPage.getLinkForEmailConfirmation().isDisplayed(), "The link for confirmation of email is not visible in the letter");
        logger.info("Link for confirmation of email is visible in the letter.");
        logger.debug("Link for confirmation of email {} is visible in the letter.", user.getEmail());
        objMailHogPage.clickOnLinkForEmailConfirmation();

        List<String> tabList = new ArrayList<>(driver.getWindowHandles());
        int size = tabList.size();
        driver.switchTo().window(tabList.get(size-1));
        assertTrue(driver.getCurrentUrl().contains("registration/confirm"), "URL should contain text 'registration/confirm'");

        objMailHogPage.verifySuccessfulConfirmationOfEmail();
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        logger.info("Message about successful confirming of the email is visible. Test completed successfully");
        logger.debug("Message about successful confirming of the email is visible. confirmPartnerRegistrationInMailHogService test completed successfully");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
