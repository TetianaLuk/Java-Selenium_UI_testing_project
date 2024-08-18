package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

public class LoginPage {

    WebDriver driver;

    @FindBy(css = "#login")
    WebElement loginInput;
    @FindBy(css = "#password")
    WebElement passwordInput;
    @FindBy(css = "button[name='login-button']")
    WebElement signInButtonOnLoginPage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public LoginPage enterLogin(String login) {
        loginInput.clear();
        WebElementUtils.enterText(loginInput, login);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInput.clear();
        WebElementUtils.enterText(passwordInput, password);
        return this;
    }

    @Step("Login with user's email and password")
    public void clickOnSignInButton() {
        WebElementUtils.clickOnButton(signInButtonOnLoginPage);
    }

    public WebElement getLoginInput() {
        return loginInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getSignInButtonOnLoginPage() {
        return signInButtonOnLoginPage;
    }
}
