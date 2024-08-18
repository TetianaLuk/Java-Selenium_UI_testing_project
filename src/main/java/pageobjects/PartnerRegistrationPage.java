package pageobjects;

import dev.failsafe.internal.util.Assert;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.WebElementUtils;

import java.io.ByteArrayInputStream;


public class PartnerRegistrationPage {
    WebDriver driver;

    @FindBy(css = "#firstName")
    WebElement firstNameInput;
    @FindBy(css = "#lastName")
    WebElement lastNameInput;
    @FindBy(css = "div[name='sex']")
    WebElement genderField;
    @FindBy(css = "#email")
    WebElement emailInput;
    @FindBy(css = "#phoneNumber")
    WebElement phoneNumberInput;
    @FindBy(css = "#password")
    WebElement passwordInput;
    @FindBy(css = "#confirmPassword")
    WebElement confPasswordInput;
    @FindBy(css = "#organizationName")
    WebElement organizationNameInput;
    @FindBy(css = "#categoryIds")
    WebElement selectCategoryField;
    @FindBy(xpath = "//input[@id='positionInOrganization']")
    WebElement positionInput;
    @FindBy(css = "#organizationSiteUrl")
    WebElement organizationLinkInput;
    @FindBy(css = "#aboutOrganization")
    WebElement aboutInput;
    @FindBy(css = "button[name='submit']")
    WebElement submitButton;
    @FindBy(css = ".display-3.text-center")
    WebElement successMessage;
    @FindBy(css = "div[name='first-name'] small[class='text-danger']")
    WebElement errorMessageUnderFirstNameField;
    @FindBy(css = "div[name='last-name'] small[class='text-danger']")
    WebElement errorMessageUnderLastNameField;
    @FindBy(css = "div[name='sex'] small[class='text-danger']")
    WebElement errorMessageUnderSexField;
    @FindBy(css = "div[name='email'] small[class='text-danger']")
    WebElement errorMessageUnderEmailField;
    @FindBy(css = "div[name='password'] small[class='text-danger']")
    WebElement errorMessageUnderPasswordField;
    @FindBy(css = "div[name='confirm-password'] small[class='text-danger']")
    WebElement errorMessageUnderConfirmPasswordField;
    @FindBy(css = "div[name='organization-name'] small[class='text-danger']")
    WebElement errorMessageUnderOrganizationNameField;
    @FindBy(css = "div[name='position-in-organization'] small[class='text-danger']")
    WebElement errorMessageUnderPositionField;
    @FindBy(css = "div[name='phone'] small[class='text-danger']")
    WebElement errorMessageUnderPhoneNumberField;

    public PartnerRegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public PartnerRegistrationPage enterFirstName(String firstName) {
        WebElementUtils.enterText(firstNameInput, firstName);
        return this;
    }

    public PartnerRegistrationPage enterLastName(String lastName) {
        WebElementUtils.enterText(lastNameInput, lastName);
        return this;
    }

    public PartnerRegistrationPage enterEmail(String email) {
        WebElementUtils.enterText(emailInput, email);
        return this;
    }

    public PartnerRegistrationPage enterPhoneNumber(String phoneNumber) {
        WebElementUtils.enterText(phoneNumberInput, phoneNumber);
        return this;
    }

    public PartnerRegistrationPage enterPassword(String password) {
        WebElementUtils.enterText(passwordInput, password);
        return this;
    }

    public PartnerRegistrationPage enterConfirmPassword(String confirmPassword) {
        WebElementUtils.enterText(confPasswordInput, confirmPassword);
        return this;
    }

    public PartnerRegistrationPage enterOrganizationName(String organizationName) {
        WebElementUtils.enterText(organizationNameInput, organizationName);
        return this;
    }

    public PartnerRegistrationPage selectCategory(String category) {
        WebElementUtils.selectCategory(selectCategoryField, category);
        return this;
    }

    public PartnerRegistrationPage enterPosition(String position) {
        WebElementUtils.enterText(positionInput, position);
        return this;
    }

    public PartnerRegistrationPage enterOrganizationLink(String organizationLink) {
        WebElementUtils.enterText(organizationLinkInput, organizationLink);
        return this;
    }

    public PartnerRegistrationPage enterAbout(String about) {
        WebElementUtils.enterText(aboutInput, about);
        return this;
    }

    @Step("Enter partner data and click on 'Sign up' button")
    public void clickOnSubmitButton() {
        WebElementUtils.clickOnButton(submitButton);
    }

    @Step("Verify successful registration message")
    public void verifySuccessfulRegistrationMassage(){
        Assert.isTrue(successMessage.isDisplayed(),"Registration failed, there is no message about success");
        Allure.addAttachment("Successful registration message", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Step("Verify error message when first name field left blank or with invalid input")
    public PartnerRegistrationPage verifyErrorMessageUnderFirstNameField(){
        String expectedMessage = "Імя не може містити символи та розділові знаки";
        String actualMessage = errorMessageUnderFirstNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Імя не може містити символи та розділові знаки' under firstName field");
        return this;
    }

    @Step("Verify error message when last name field left blank or with invalid input")
    public PartnerRegistrationPage verifyErrorMessageUnderLastNameField(){
        String expectedMessage = "Призвище не може містити символи та розділові знаки";
        String actualMessage = errorMessageUnderLastNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Призвище не може містити символи та розділові знаки' under lastName field");
        return this;
    }

    @Step("Verify error message when sex field left blank")
    public PartnerRegistrationPage verifyErrorMessageWhenSexFieldLeftBlank(){
        String expectedMessage = "Поле не може бути порожнім";
        String actualMessage = errorMessageUnderSexField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Поле не може бути порожнім' under sex field");
        return this;
    }

    @Step("Verify error message when email field left blank")
    public PartnerRegistrationPage verifyErrorMessageWhenEmailFieldLeftBlank(){
        String expectedMessage = "Адреса повинна бути корпоративною";
        String actualMessage = errorMessageUnderEmailField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Адреса повинна бути корпоративною' under email field");
        return this;
    }

    @Step("Verify error message when password field left blank")
    public PartnerRegistrationPage verifyErrorMessageWhenPasswordFieldLeftBlank(){
        String expectedMessage = "Поле не може бути порожнім";
        String actualMessage = errorMessageUnderPasswordField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Поле не може бути порожнім' under password field");
        return this;
    }

    @Step("Verify error message when confirm password field left blank")
    public PartnerRegistrationPage verifyErrorMessageWhenConfirmPasswordFieldLeftBlank(){
        String expectedMessage = "Поле не може бути порожнім";
        String actualMessage = errorMessageUnderConfirmPasswordField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Поле не може бути порожнім' under confirm password field");
        return this;
    }

    @Step("Verify error message when organization name field left blank or with invalid input")
    public PartnerRegistrationPage verifyErrorMessageUnderOrganizationNameField(){
        String expectedMessage = "Не должно содержать символов";
        String actualMessage = errorMessageUnderOrganizationNameField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Не должно содержать символов' under organization name field");
        return this;
    }

    @Step("Verify error message when position field left blank or with invalid input")
    public void verifyErrorMessageUnderPositionField(){
        String expectedMessage = "Не може містити символи";
        String actualMessage = errorMessageUnderPositionField.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"There is no message 'Не може містити символи' under position field");
    }

    @Step("Verify error message when email field with invalid input")
    public void verifyErrorMessageWhenEmailFieldWithInvalidInput(){
        String expectedMessage = "Електронна адреса має містити знак \"@\". В електронній адресі \"ashley.anderson.skarb.ngo\" знака \"@\" немає.";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("var emailField = document.querySelector('#email'); return emailField.validationMessage;");
        Assert.isTrue(expectedMessage.equals(validationMessage),"There is no alert message 'Електронна адреса має містити знак \"@\". В електронній адресі \"ashley.anderson.skarb.ngo\" знака \"@\" немає' under email field");
    }

    @Step("Verify error message when phone number field with invalid input")
    public void verifyErrorMessageWhenPhoneNumberFieldWithInvalidInput(){
        String expectedMessage = "Номер телефону введено не коректно";
        String actualMessage = errorMessageUnderPhoneNumberField .getText();
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

    public WebElement getGenderField() {
        return genderField;
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

    public WebElement getOrganizationNameInput() {
        return organizationNameInput;
    }

    public WebElement getSelectCategoryField() {
        return selectCategoryField;
    }

    public WebElement getPositionInput() {
        return positionInput;
    }

    public WebElement getOrganizationLinkInput() {
        return organizationLinkInput;
    }

    public WebElement getAboutInput() {
        return aboutInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getSuccessMessage() {
        return successMessage;
    }

}
