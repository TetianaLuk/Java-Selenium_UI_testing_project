package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

public class SkarbRegistrationPage {

    WebDriver driver;

    @FindBy(css = "button[class='btn btn-primary btn-lg btn-block']")
    WebElement createVolunteerButton;
    @FindBy(css = "button[class='btn btn-success btn-lg btn-block']")
    WebElement createPartnerButton;
    @FindBy(css = "button[class='btn btn-warning btn-lg btn-block']")
    WebElement createNGO_Button;

    public SkarbRegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Click on 'Volunteer' button")
    public VolunteerRegistrationPage clickOnCreateNewVolunteer() {
        WebElementUtils.clickOnButton(createVolunteerButton);
        return new VolunteerRegistrationPage(driver);
    }

    @Step("Click on 'Partner' button")
    public PartnerRegistrationPage clickOnCreateNewPartner() {
        WebElementUtils.clickOnButton(createPartnerButton);
        return new PartnerRegistrationPage(driver);
    }

    @Step("Click on 'Nongovernmental organization' button")
    public NGO_RegistrationPage clickOnCreateNewNGO() {
        WebElementUtils.clickOnButton(createNGO_Button);
        return new NGO_RegistrationPage(driver);
    }

}
