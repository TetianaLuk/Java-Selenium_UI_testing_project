package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

public class NGO_ProfilePage {

    WebDriver driver;

    @FindBy(xpath = "//span[contains(text(),'Мої задачі')]")
    WebElement myTasksDropdown;
    @FindBy(xpath = "//span[contains(text(),'Опубліковані')]")
    WebElement publishedTasksButton;
    @FindBy(css = ".fa.fa-sign-out.fa-3x.text-dark-red")
    WebElement signOutButton;

    public NGO_ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnMyTasksDropdown() {
        WebElementUtils.clickOnButton(myTasksDropdown);
    }

    public void clickOnPublishedTasksButton() {
        WebElementUtils.clickOnButton(publishedTasksButton);
    }

    public void clickOnSignOutButton() {
        WebElementUtils.clickOnButton(signOutButton);
    }

    public WebElement getMyTasksDropdown() {
        return myTasksDropdown;
    }

    public WebElement getPublishedTasksButton() {
        return publishedTasksButton;
    }

    public WebElement getSignOutButton() {
        return signOutButton;
    }
}
