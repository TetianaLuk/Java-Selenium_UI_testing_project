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

public class NGO_RegistrationPage {

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
    @FindBy(css = ".filter-option-inner-inner")
    WebElement selectedCategory;
    @FindBy(xpath = "//input[@id='positionInOrganization']")
    WebElement positionInput;
    @FindBy(css = "#organizationSiteUrl")
    WebElement organizationLinkInput;
    @FindBy(css = "#aboutOrganization")
    WebElement aboutInput;
    @FindBy(css = "#organizationRegistrationUrl")
    WebElement organizationRegistrationUrlInput;
    @FindBy(css = "button[name='submit']")
    WebElement submitButton;
    @FindBy(css = ".display-3.text-center")
    WebElement successMessage;

    public NGO_RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public NGO_RegistrationPage enterFirstName(String firstName) {
        WebElementUtils.enterText(firstNameInput, firstName);
        return this;
    }

    public NGO_RegistrationPage enterLastName(String lastName) {
        WebElementUtils.enterText(lastNameInput, lastName);
        return this;
    }

    public NGO_RegistrationPage enterEmail(String email) {
        WebElementUtils.enterText(emailInput, email);
        return this;
    }

    public NGO_RegistrationPage enterPhoneNumber(String phoneNumber) {
        WebElementUtils.enterText(phoneNumberInput, phoneNumber);
        return this;
    }

    public NGO_RegistrationPage enterPassword(String password) {
        WebElementUtils.enterText(passwordInput, password);
        return this;
    }

    public NGO_RegistrationPage enterConfirmPassword(String confirmPassword) {
        WebElementUtils.enterText(confPasswordInput, confirmPassword);
        return this;
    }

    public NGO_RegistrationPage enterOrganizationName(String organizationName) {
        WebElementUtils.enterText(organizationNameInput, organizationName);
        return this;
    }

    public NGO_RegistrationPage selectCategory(String category) {
        WebElementUtils.selectCategory(selectCategoryField, category);
        return this;
    }

    public NGO_RegistrationPage enterPosition(String position) {
        WebElementUtils.enterText(positionInput, position);
        return this;
    }

    public NGO_RegistrationPage enterOrganizationLink(String organizationLink) {
        WebElementUtils.enterText(organizationLinkInput, organizationLink);
        return this;
    }

    public NGO_RegistrationPage enterAbout(String about) {
        WebElementUtils.enterText(aboutInput, about);
        return this;
    }

    public NGO_RegistrationPage enterOrganizationRegistrationUrl(String organizationRegistrationUrl) {
        WebElementUtils.enterText(organizationRegistrationUrlInput, organizationRegistrationUrl);
        return this;
    }

    @Step("Enter NGO data and click on 'Sign up' button")
    public void clickOnSubmitButton() {
        WebElementUtils.clickOnButton(submitButton);
    }

    @Step("Verify successful registration message")
    public void verifySuccessfulRegistrationMassage(){
        Assert.isTrue(successMessage.isDisplayed(),"Registration failed, there is no message about success");
        Allure.addAttachment("Successful registration message", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    public void populateNGOregistrationForm(String firstName, String lastName, String email, String phoneNumber, String password, String confirmPassword, String organizationName, String category, String position, String organizationLink, String about, String organizationRegistrationUrl){
        WebElementUtils.enterText(firstNameInput, firstName);
        WebElementUtils.enterText(lastNameInput, lastName);
        WebElementUtils.enterText(emailInput, email);
        WebElementUtils.enterText(phoneNumberInput, phoneNumber);
        WebElementUtils.enterText(passwordInput, password);
        WebElementUtils.enterText(confPasswordInput, confirmPassword);
        WebElementUtils.enterText(organizationNameInput, organizationName);
        WebElementUtils.selectCategory(selectCategoryField, category);
        WebElementUtils.enterText(positionInput, position);
        WebElementUtils.enterText(organizationLinkInput, organizationLink);
        WebElementUtils.enterText(aboutInput, about);
        WebElementUtils.enterText(organizationRegistrationUrlInput, organizationRegistrationUrl);
    }

    public boolean verifyNGOregistrationFormPopulatedCorrectly(String firstName, String lastName, String email, String phoneNumber, String password, String confirmPassword, String organizationName, String category, String position, String organizationLink, String about, String organizationRegistrationUrl){
        return firstNameInput.getAttribute("value").equals(firstName) &&
                lastNameInput.getAttribute("value").equals(lastName)&&
                emailInput.getAttribute("value").equals(email) &&
                phoneNumberInput.getAttribute("value").equals(phoneNumber) &&
                passwordInput.getAttribute("value").equals(password) &&
                confPasswordInput.getAttribute("value").equals(confirmPassword) &&
                organizationNameInput.getAttribute("value").equals(organizationName) &&
                selectedCategory.getText().equals(category) &&
                positionInput.getAttribute("value").equals(position) &&
                organizationLinkInput.getAttribute("value").equals(organizationLink) &&
                aboutInput.getAttribute("value").equals(about) &&
                organizationRegistrationUrlInput.getAttribute("value").equals(organizationRegistrationUrl);
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

    public WebElement getGenderField() {
        return genderField;
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

    public WebElement getOrganizationRegistrationUrlInput() {
        return organizationRegistrationUrlInput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getSuccessMessage() {
        return successMessage;
    }
}
