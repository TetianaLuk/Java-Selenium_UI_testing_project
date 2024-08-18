package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pageobjects.*;
import models.TaskForVolunteer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import java.io.IOException;
import java.util.Properties;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

public class NGOtasksForVolunteerParameterizedTest {

    SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
    CreatingNewTaskForVolunteerPage objCreatingNewTaskForVolunteerPage = new CreatingNewTaskForVolunteerPage(driver);

    private static final Logger logger = LogManager.getLogger(NGOtasksForVolunteerParameterizedTest.class);

    @BeforeAll
    public static void setUpAndLogIn() throws IOException {
        chromeSetUp();
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
        objSkarbHomePage.clickOnSelectWebSiteLanguage()
                        .clickOnSelectUkrLanguage();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", objSkarbHomePage.getSignInButtonOnHomePage());
        js.executeScript("arguments[0].click();", objSkarbHomePage.getSignInButtonOnHomePage());

        LoginPage objLoginPage = new LoginPage(driver);
        assertAll("Checking the loading of the login page",
                () -> assertTrue(objLoginPage.getLoginInput().isDisplayed(), "loginInput field is invisible"),
                () -> assertTrue(objLoginPage.getPasswordInput().isDisplayed(), "passwordInput field is invisible"),
                () -> assertTrue(objLoginPage.getSignInButtonOnLoginPage().isEnabled(), "signInButton on login page is disable")
                );

        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        objLoginPage.enterLogin(properties.getProperty("login_NGOuser"))
                    .enterPassword(properties.getProperty("password_NGOuser"))
                    .clickOnSignInButton();

        logger.info("Set up the driver, navigated to initial webpage.User logged in.");
    }

    @ParameterizedTest
    @MethodSource("testdata.TaskForVolunteerParameterizedTestData#getTaskForVolunteerParameterizedTestData")
    @DisplayName("Create new task for Volunteer with parameterized test and @MethodSource")
    @Description("This test attempts to create new task for volunteer using valid data from @MethodSource.")
    @Severity(CRITICAL)
    public void createTaskForVolunteerWithMethodSource(TaskForVolunteer task) {

        assertTrue(objSkarbHomePage.getNavigateToHomePageButton().isEnabled(), "navigateToHomePageButton is disable");
        objSkarbHomePage.clickOnNavigateToHomePageButton();

        logger.info("createTaskForVolunteer test started.");

        assertTrue(objSkarbHomePage.getTasksDropdown().isEnabled(), "tasksDropdown is disable");
        objSkarbHomePage.clickOnTasksDropdown();

        assertTrue(objSkarbHomePage.getCreateTaskForVolunteerButton().isEnabled(), "createTaskForVolunteerButton is disable");
        objSkarbHomePage.clickOnCreateTaskForVolunteerButton();

        assertTrue(driver.getCurrentUrl().contains("tasks/register/volunteer"), "URL should contain text 'tasks/register/volunteer'");

        logger.info("Entering task data.");

        objCreatingNewTaskForVolunteerPage.enterTaskName(task.getTaskName())
                                            .selectCategory(task.getSelectCategory())
                                            .setTaskDeadline(5)
                                            .enterTaskDescription(task.getTaskDescription())
                                            .enterExpectedOutcome(task.getExpectedOutcome())
                                            .enterVolunteerBenefit(task.getVolunteerBenefit())
                                            .enterRequirementsToVolunteer(task.getRequirementsToVolunteer())
                                            .clickOnInterviewRequiredCheckBox()
                                            .enterSavedMoney(task.getSavedMoney())
                                            .enterFirstWorkStageName(task.getFirstWorkStageName())
                                            .enterFirstWorkStageDuration(task.getFirstWorkStageDuration())
                                            .enterFirstWorkStageDescription(task.getFirstWorkStageDescription())
                                            .enterSecondWorkStageName(task.getSecondWorkStageName())
                                            .enterSecondWorkStageDuration(task.getSecondWorkStageDuration())
                                            .enterSecondWorkStageDescription(task.getSecondWorkStageDescription())
                                            .clickOnCreateAndPublishTaskButton();


        objCreatingNewTaskForVolunteerPage.verifySuccessfulCreatingAndPublishingOfTaskMassage();
        logger.info("Task is created and published. Message about successful creating and publishing of the task is visible.");

        assertAll("Checking the content of the task",
                () -> assertTrue(driver.findElement(By.xpath("//span[normalize-space()='" + task.getTaskName() + "']")).isDisplayed(), "Created task contains correct task name."),
                () -> assertEquals(task.getTaskDescription(), driver.findElement(By.xpath("//div[@name='task-description']")).getText(), "Created task contains correct description."),
                () -> assertEquals(task.getVolunteerBenefit(), driver.findElement(By.xpath("//div[@name='volunteer-benefit']")).getText(), "The Select Category of the created task is not visible in the list of published tasks.")
        );

        logger.info("Created task is present in the list of published tasks and contains given data.");
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/testdata/dataForTasksForVolunteer.csv", numLinesToSkip = 1)
    @DisplayName("Create new task for Volunteer with parameterized test and @CsvFileSource")
    @Description("This test attempts to create new task for volunteer using valid data from @CsvFileSource.")
    @Severity(CRITICAL)
    public void createTaskForVolunteerWithCsvFileSource(String taskName, String selectCategory, int numberOfDaysToTaskDeadline, String taskDescription, String expectedOutcome, String volunteerBenefit, String requirementsToVolunteer, String savedMoney, String firstWorkStageName, String firstWorkStageDuration, String firstWorkStageDescription, String secondWorkStageName, String secondWorkStageDuration, String secondWorkStageDescription) {

        assertTrue(objSkarbHomePage.getNavigateToHomePageButton().isEnabled(), "navigateToHomePageButton is disable");
        objSkarbHomePage.clickOnNavigateToHomePageButton();

        logger.info("createTaskForVolunteerWithCsvFileSource test started.");

        assertTrue(objSkarbHomePage.getTasksDropdown().isEnabled(), "tasksDropdown is disable");
        objSkarbHomePage.clickOnTasksDropdown();

        assertTrue(objSkarbHomePage.getCreateTaskForVolunteerButton().isEnabled(), "createTaskForVolunteerButton is disable");
        objSkarbHomePage.clickOnCreateTaskForVolunteerButton();

        assertTrue(driver.getCurrentUrl().contains("tasks/register/volunteer"), "URL should contain text 'tasks/register/volunteer'");

        logger.info("Entering task data.");

        objCreatingNewTaskForVolunteerPage.enterTaskName(taskName)
                .selectCategory(selectCategory)
                .setTaskDeadline(numberOfDaysToTaskDeadline)
                .enterTaskDescription(taskDescription)
                .enterExpectedOutcome(expectedOutcome)
                .enterVolunteerBenefit(volunteerBenefit)
                .enterRequirementsToVolunteer(requirementsToVolunteer)
                .clickOnInterviewRequiredCheckBox()
                .enterSavedMoney(savedMoney)
                .enterFirstWorkStageName(firstWorkStageName)
                .enterFirstWorkStageDuration(firstWorkStageDuration)
                .enterFirstWorkStageDescription(firstWorkStageDescription)
                .enterSecondWorkStageName(secondWorkStageName)
                .enterSecondWorkStageDuration(secondWorkStageDuration)
                .enterSecondWorkStageDescription(secondWorkStageDescription)
                .clickOnCreateAndPublishTaskButton();

        objCreatingNewTaskForVolunteerPage.verifySuccessfulCreatingAndPublishingOfTaskMassage();
        logger.info("Task is created and published. Message about successful creating and publishing of the task is visible.");

        assertAll("Checking the content of the task",
                () -> assertTrue(driver.findElement(By.xpath("//span[normalize-space()='" + taskName + "']")).isDisplayed(), "Created task contains correct task name."),
                () -> assertEquals(taskDescription, driver.findElement(By.xpath("//div[@name='task-description']")).getText(), "Created task contains correct description."),
                () -> assertEquals(volunteerBenefit, driver.findElement(By.xpath("//div[@name='volunteer-benefit']")).getText(), "The Select Category of the created task is not visible in the list of published tasks.")
        );

        logger.info("Created task is present in the list of published tasks and contains given data.");
    }

    @AfterAll
    public static void signOutAndTearDown() {
        WebElement signOutButton = new NGO_ProfilePage(driver).getSignOutButton();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",signOutButton);
        js.executeScript("arguments[0].click();", signOutButton);

        assertEquals("Ви успішно вийшли з аккаунту.", driver.findElement(By.xpath("//p[@class='text-dark']")) .getText(), "The user is not logged out." );
        logger.info("The user is logged out.");

        if (driver != null) {
            driver.quit();
        }
    }
}
