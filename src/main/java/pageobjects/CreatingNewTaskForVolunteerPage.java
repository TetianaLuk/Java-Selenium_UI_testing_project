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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreatingNewTaskForVolunteerPage {
    WebDriver driver;

    @FindBy(name = "name")
    WebElement taskNameInput;
    @FindBy(css = "#categoryIds")
    WebElement selectCategoryField;
    @FindBy(name = "deadline")
    WebElement deadlineInput;
    @FindBy(name = "description")
    WebElement taskDescriptionInput;
    @FindBy(name = "expectedOutcome")
    WebElement expectedOutcomeInput;
    @FindBy(name = "benefit")
    WebElement volunteerBenefitInput;
    @FindBy(css = "input[id='requirements[0]']")
    WebElement requirementsToVolunteerInput;
    @FindBy(name = "interviewRequired")
    WebElement interviewRequiredCheckBox;
    @FindBy(name = "savedMoney")
    WebElement savedMoneyInput;
    @FindBy(xpath = "(//input[@name='stages[0].name'])[1]")
    WebElement firstWorkStageNameInput;
    @FindBy(xpath = "(//input[@name='stages[0].duration'])[1]")
    WebElement firstWorkStageDurationInput;
    @FindBy(css = "textarea[name='stages[0].description']")
    WebElement firstWorkStageDescriptionInput;
    @FindBy(xpath = "(//input[@name='stages[1].name'])[1]")
    WebElement secondWorkStageNameInput;
    @FindBy(xpath = "(//input[@name='stages[1].duration'])[1]")
    WebElement secondWorkStageDurationInput;
    @FindBy(css = "textarea[name='stages[1].description']")
    WebElement secondWorkStageDescriptionInput;
    @FindBy(css = "button[value='PUBLISHED']")
    WebElement createAndPublishTaskButton;
    @FindBy(xpath = "//h4[contains(text(),'Нове завдання було успішно зареєстровано та опублі')]")
    WebElement successMessage;

    public CreatingNewTaskForVolunteerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CreatingNewTaskForVolunteerPage enterTaskName (String taskName) {
        WebElementUtils.enterText(taskNameInput, taskName);
        return this;
    }

    public CreatingNewTaskForVolunteerPage selectCategory (String category) {
        WebElementUtils.selectCategory(selectCategoryField, category);
        return this;
    }

    public CreatingNewTaskForVolunteerPage setTaskDeadline(int taskDuration){
        LocalDate deadLine = LocalDate.now().plusDays(taskDuration);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = deadLine.format(formatter);
        WebElementUtils.enterText(deadlineInput, text);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterTaskDescription (String taskDescription) {
        WebElementUtils.enterText(taskDescriptionInput, taskDescription);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterExpectedOutcome (String expectedOutcome) {
        WebElementUtils.enterText(expectedOutcomeInput, expectedOutcome);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterVolunteerBenefit (String volunteerBenefit) {
        WebElementUtils.enterText(volunteerBenefitInput, volunteerBenefit);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterRequirementsToVolunteer (String requirementsToVolunteer) {
        WebElementUtils.enterText(requirementsToVolunteerInput, requirementsToVolunteer);
        return this;
    }

    public CreatingNewTaskForVolunteerPage clickOnInterviewRequiredCheckBox () {
        WebElementUtils.clickOnButton(interviewRequiredCheckBox);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterSavedMoney (String savedMoney) {
        WebElementUtils.enterText(savedMoneyInput, savedMoney);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterFirstWorkStageName (String firstWorkStageName) {
        firstWorkStageNameInput.clear();
        WebElementUtils.enterText(firstWorkStageNameInput, firstWorkStageName);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterFirstWorkStageDuration (String firstWorkStageDuration) {
        firstWorkStageDurationInput.clear();
        WebElementUtils.enterText(firstWorkStageDurationInput, firstWorkStageDuration);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterFirstWorkStageDescription (String firstWorkStageDescription) {
        firstWorkStageDescriptionInput.clear();
        WebElementUtils.enterText(firstWorkStageDescriptionInput, firstWorkStageDescription);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterSecondWorkStageName (String secondWorkStageName) {
        secondWorkStageNameInput.clear();
        WebElementUtils.enterText(secondWorkStageNameInput, secondWorkStageName);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterSecondWorkStageDuration (String secondWorkStageDuration) {
        secondWorkStageDurationInput.clear();
        WebElementUtils.enterText(secondWorkStageDurationInput, secondWorkStageDuration);
        return this;
    }

    public CreatingNewTaskForVolunteerPage enterSecondWorkStageDescription (String secondWorkStageDescription) {
        secondWorkStageDescriptionInput.clear();
        WebElementUtils.enterText(secondWorkStageDescriptionInput, secondWorkStageDescription);
        return this;
    }

    @Step("Enter task data and click on 'Create and publish task' button")
    public void clickOnCreateAndPublishTaskButton() {
        WebElementUtils.clickOnButton(createAndPublishTaskButton);
    }

    @Step("Verify message about successful creating and publishing of the task")
    public void verifySuccessfulCreatingAndPublishingOfTaskMassage(){
        String expectedMessage = "Нове завдання було успішно зареєстровано та опубліковано.";
        String actualMessage = successMessage.getText();
        Assert.isTrue(expectedMessage.equals(actualMessage),"Task has not been created and published, there is no message about success.");
        Allure.addAttachment("Message about successful creating and publishing of the task", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    public WebElement getSelectCategoryField() {
        return selectCategoryField;
    }

    public WebElement getSuccessMessage() {
        return successMessage;
    }
}
