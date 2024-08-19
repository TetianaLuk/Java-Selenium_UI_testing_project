package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

public class SkarbHomePage {

    WebDriver driver;

    @FindBy(xpath = "//i[@class='fa fa-language fa-lg']")
    WebElement selectWebSiteLanguageButton;
    @FindBy(xpath = "//a[contains(text(),'Укр')]")
    WebElement selectUkrLanguageButton;
    @FindBy(css = ".fa.fa-user-plus.fa-3x.text-dark-red")
    WebElement createNewUserButton;
    @FindBy(xpath = "//i[@class='fa fa-sign-in fa-3x text-dark-red']")
    WebElement signInButtonOnHomePage;
    @FindBy(css = "h4[class='text-dark-red']")
    WebElement navigateToHomePageButton;
    @FindBy(css = "h5[id='tasksDropdown'] span")
    WebElement tasksDropdown;
    @FindBy(xpath = "//a[contains(text(),'Створити завдання для волонтера')]")
    WebElement createTaskForVolunteerButton;
    @FindBy(css = ".fa.fa-user-circle-o.fa-3x.text-dark-red")
    WebElement navigateToUserProfilePageButton;

    public SkarbHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SkarbHomePage clickOnSelectWebSiteLanguage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", selectWebSiteLanguageButton);
        js.executeScript("arguments[0].click();", selectWebSiteLanguageButton);
        return this;
    }

    public SkarbHomePage clickOnSelectUkrLanguage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", selectUkrLanguageButton);
        js.executeScript("arguments[0].click();", selectUkrLanguageButton);
        return this;
    }

    @Step("Click on 'Create a new user' button")
    public SkarbRegistrationPage clickOnCreateNewUser() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", createNewUserButton);
        js.executeScript("arguments[0].click();", createNewUserButton);
        return new SkarbRegistrationPage(driver);
    }

    @Step("Click on 'Sign in' button on the Skarb home page")
    public LoginPage clickOnSignInButtonOnHomePage() {
        WebElementUtils.clickOnButton(signInButtonOnHomePage);
        return new LoginPage(driver);
    }

    public void clickOnNavigateToHomePageButton() {
        WebElementUtils.clickOnButton(navigateToHomePageButton);
    }

    public void clickOnTasksDropdown() {
        WebElementUtils.clickOnButton(tasksDropdown);
    }

    @Step("After user is logged in, click on task dropdown and then click on 'Create task for volunteer' button")
    public void clickOnCreateTaskForVolunteerButton() {
        WebElementUtils.clickOnButton(createTaskForVolunteerButton);
    }

    public void clickOnNavigateToUserProfilePageButton() {
        WebElementUtils.clickOnButton(navigateToUserProfilePageButton);
    }

    public WebElement getNavigateToHomePageButton() {
        return navigateToHomePageButton;
    }

    public WebElement getTasksDropdown() {
        return tasksDropdown;
    }

    public WebElement getCreateTaskForVolunteerButton() {
        return createTaskForVolunteerButton;
    }

    public WebElement getNavigateToUserProfilePageButton() {
        return navigateToUserProfilePageButton;
    }

    public WebElement getSignInButtonOnHomePage() {
        return signInButtonOnHomePage;
    }
}
