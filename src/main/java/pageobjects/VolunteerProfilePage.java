package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

public class VolunteerProfilePage {

    WebDriver driver;

    @FindBy(css = ".fa.fa-user-circle-o.fa-3x.text-dark-red")
    WebElement userProfileButton;
    @FindBy(css = ".fa.fa-sign-out.fa-3x.text-dark-red")
    WebElement signOutButton;

    public VolunteerProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Click on 'Sign out' button to log out from user profile.")
    public void clickOnSignOutButton() {
        WebElementUtils.clickOnButton(signOutButton);
    }

    public WebElement getUserProfileButton() {
        return userProfileButton;
    }
}
