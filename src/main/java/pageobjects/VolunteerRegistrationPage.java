package pageobjects;

import dev.failsafe.internal.util.Assert;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;
import java.io.ByteArrayInputStream;

public class VolunteerRegistrationPage {

    WebDriver driver;

    @FindBy(name = "firstName")
    WebElement firstNameInput;
    @FindBy(name = "lastName")
    WebElement lastNameInput;
    @FindBy(id = "email")
    WebElement emailInput;
    @FindBy(id = "phoneNumber")
    WebElement phoneNumberInput;
    @FindBy(id = "password")
    WebElement passwordInput;
    @FindBy(id = "confirmPassword")
    WebElement confPasswordInput;
    @FindBy(id = "about")
    WebElement aboutInput;
    @FindBy(name = "categoryIds")
    WebElement selectCategoryField;
    @FindBy(name = "submit")
    WebElement submitButton;
    @FindBy(css = ".display-3.text-center")
    WebElement successMessage;
    @FindBy(css = "div[name='first-name'] small[class='text-danger']")
    WebElement errorMessageUnderFirstNameField;
    @FindBy(css = "div[name='last-name'] small[class='text-danger']")
    WebElement errorMessageUnderLastNameField;
    @FindBy(css = "div[name='email'] small[class='text-danger']")
    WebElement errorMessageUnderEmailField;
    @FindBy(css = "div[name='password'] small[class='text-danger']")
    WebElement errorMessageUnderPasswordField;
    @FindBy(css = "div[name='confirm-password'] small[class='text-danger']")
    WebElement errorMessageUnderConfirmPasswordField;
    @FindBy(css = "div[name='phone'] small[class='text-danger']")
    WebElement errorMessageUnderPhoneNumberField;

    public VolunteerRegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public VolunteerRegistrationPage enterFirstName(String firstName) {
        WebElementUtils.enterText(firstNameInput, firstName);
        return this;
    }

    public VolunteerRegistrationPage enterLastName(String lastName) {
        WebElementUtils.enterText(lastNameInput, lastName);
        return this;
    }

    public VolunteerRegistrationPage enterEmail(String email) {
        WebElementUtils.enterText(emailInput, email);
        return this;
    }

    public VolunteerRegistrationPage enterPhoneNumber(String phoneNumber) {
        WebElementUtils.enterText(phoneNumberInput, phoneNumber);
        return this;
    }

    public VolunteerRegistrationPage enterPassword(String password) {
        WebElementUtils.enterText(passwordInput, password);
        return this;
    }

    public VolunteerRegistrationPage enterConfirmPassword(String confirmPassword) {
        WebElementUtils.enterText(confPasswordInput, confirmPassword);
        return this;
    }

    public VolunteerRegistrationPage enterAbout(String about) {
        WebElementUtils.enterText(aboutInput, about);
        return this;
    }

    public VolunteerRegistrationPage selectCategory(String category) {
        WebElementUtils.selectCategory(selectCategoryField, category);
        return this;
    }

    @Step("Enter volunteer data and click on 'Sign up' button")
    public void clickOnSubmitButton() {
        WebElementUtils.clickOnButton(submitButton);
    }

    @Step("Verify successful registration message")
    public void verifySuccessfulRegistrationMassage(){
        Assert.isTrue(successMessage.isDisplayed(),"Registration failed, there is no message about success");
        Allure.addAttachment("Successful registration message", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Step("Verify error message when first name field left blank")
    public VolunteerRegistrationPage verifyErrorMessageWhenFirstNameFieldLeftBlank(){
        String expectedMessage = "Field can`t be empty";
        String actualMessage = errorMessageUnderFirstNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Field can`t be empty' under firstName field");
        return this;
    }

    @Step("Verify error message when last name field left blank")
    public VolunteerRegistrationPage verifyErrorMessageWhenLastNameFieldLeftBlank(){
        String expectedMessage = "Field can`t be empty";
        String actualMessage = errorMessageUnderLastNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Field can`t be empty' under lastName field");
        return this;
    }

    @Step("Verify error message when email field left blank")
    public VolunteerRegistrationPage verifyErrorMessageWhenEmailFieldLeftBlank(){
        String expectedMessage = "Field can`t be empty";
        String actualMessage = errorMessageUnderEmailField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Field can`t be empty' under email field");
        return this;
    }

    @Step("Verify error message when password field left blank")
    public VolunteerRegistrationPage verifyErrorMessageWhenPasswordFieldLeftBlank(){
        String expectedMessage = "Field can`t be empty";
        String actualMessage = errorMessageUnderPasswordField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Field can`t be empty' under password field");
        return this;
    }

    @Step("Verify error message when confirm password field left blank")
    public void verifyErrorMessageWhenConfirmPasswordFieldLeftBlank(){
        String expectedMessage = "Field can`t be empty";
        String actualMessage = errorMessageUnderConfirmPasswordField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Field can`t be empty' under confirm password field");
    }

    @Step("Verify error message when first name field with invalid input")
    public VolunteerRegistrationPage verifyErrorMessageWhenFirstNameFieldWithInvalidInput(){
        String expectedMessage = "Імя не може містити символи та розділові знаки";
        String actualMessage = errorMessageUnderFirstNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Імя не може містити символи та розділові знаки' under firstName field");
        return this;
    }

    @Step("Verify error message when last name field with invalid input")
    public void verifyErrorMessageWhenLastNameFieldWithInvalidInput(){
        String expectedMessage = "Призвище не може містити символи та розділові знаки";
        String actualMessage = errorMessageUnderLastNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Призвище не може містити символи та розділові знаки' under lastName field");
    }

    @Step("Verify error message when email field with invalid input")
    public void verifyErrorMessageWhenEmailFieldWithInvalidInput(){
        String expectedMessage = "Email введено не коректно";
        String actualMessage = errorMessageUnderEmailField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Email введено не коректно' under email field");
    }

    @Step("Verify error message when phone number field with invalid input")
    public void verifyErrorMessageWhenPhoneNumberFieldWithInvalidInput(){
        String expectedMessage = "Номер телефону введено не коректно";
        String actualMessage = errorMessageUnderPhoneNumberField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Номер телефону введено не коректно' under phone number field");
    }

    @Step("Verify error message when password field with invalid input")
    public void verifyErrorMessageWhenPasswordFieldWithInvalidInput(){
        String expectedMessage = "Пароль введено не коректно. Максимальна довжина паролю 100, мінімальна довжина паролю 8, має містити: великих літер - щонайменше 2, малих літер - щонайменше 2, спеціальних символів - 1";
        String actualMessage = errorMessageUnderPasswordField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Пароль введено не коректно. Максимальна довжина паролю 100, мінімальна довжина паролю 8, має містити: великих літер - щонайменше 2, малих літер - щонайменше 2, спеціальних символів - 1' under password field");
    }

    @Step("Verify error message when confirm password field with invalid input")
    public void verifyErrorMessageWhenConfirmPasswordFieldWithInvalidInput(){
        String expectedMessage = "Password and confirm password isn`t equal";
        String actualMessage = errorMessageUnderConfirmPasswordField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Password and confirm password isn`t equal' under confirm password field");
    }

    public WebElement getFirstNameInput() {
        return firstNameInput;
    }

    public WebElement getLastNameInput() {
        return lastNameInput;
    }

    public WebElement getEmailInput() {
        return emailInput;
    }

    public WebElement getPhoneNumberInput() {
        return phoneNumberInput;
    }

    public WebElement getPasswordInput() {
        return passwordInput;
    }

    public WebElement getConfPasswordInput() {
        return confPasswordInput;
    }

    public WebElement getAboutInput() {
        return aboutInput;
    }

    public WebElement getSelectCategoryField() {
        return selectCategoryField;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getSuccessMessage() {
        return successMessage;
    }
}
