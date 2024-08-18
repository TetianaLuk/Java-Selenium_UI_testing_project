package pageobjects;

import dev.failsafe.internal.util.Assert;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class MailHogPage {

    WebDriver driver;

    @FindBy(css = ".ng-binding.ng-scope")
    List<WebElement> listOfEmailsPresentOnPage;
    @FindBy(partialLinkText = "https://skarb.foxmin")
    WebElement linkForEmailConfirmation;
    @FindBy(css = ".display-3.text-center")
    WebElement finalSuccessMessage;

    public MailHogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Click on link for user email confirmation")
    public void clickOnLinkForEmailConfirmation() {
        WebElementUtils.clickOnButton(linkForEmailConfirmation);
    }

    public String getMailHogURL () throws IOException {
        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        return properties.getProperty("URL_MailHog");
    }

    @Step("Check if successfully navigated to MailHog service webpage")
    public void checkIfSuccessfullyNavigatedToMailHogWebpage() {
        Assert.isTrue(driver.getCurrentUrl().contains("skarbmail.foxminded"), "URL should contain text 'skarbmail.foxminded'");
    }

    @Step("Verify that letter about successful registration of user is present in the MailHog service")
    public void verifyLetterAboutSuccessfulRegistrationIsPresentOnWebPage(String email) {
        List<WebElement> listOfElements = listOfEmailsPresentOnPage;
        int listOfElementsSize = listOfElements.size();
        for (int i=0; i<listOfElementsSize; i++) {
            if (listOfEmailsPresentOnPage.get(i).getText().equals(email)) {
                System.out.println("There is a letter about successful registration in the MailHog ");
                }
        }
        Assert.isTrue(driver.findElement(By.xpath("//div[contains(text(),'" + email + "')]")).isDisplayed(), "The letter about successful registration is not visible in the MailHog");
    }

    @Step("Verify message about successful confirmation of the email")
    public void verifySuccessfulConfirmationOfEmail(){
        String expectedMessage = "Ваш email підтверджено!";
        String actualMessage = finalSuccessMessage.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message about successful confirmation of the email on the webpage");
        System.out.println("Message about successful confirmation of the email is visible");
    }

    public List<WebElement> getListOfEmailsPresentOnPage() {
        return listOfEmailsPresentOnPage;
    }

    public WebElement getLinkForEmailConfirmation() {
        return linkForEmailConfirmation;
    }

    public WebElement getFinalSuccessMessage() {
        return finalSuccessMessage;
    }
}
