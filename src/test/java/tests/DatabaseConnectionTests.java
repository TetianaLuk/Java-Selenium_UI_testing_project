package tests;

import database.DatabaseConnectionManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.instancio.Instancio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import pageobjects.*;
import utils.WebElementUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.*;
import static utils.DriverSetUp.chromeSetUp;
import static utils.DriverSetUp.driver;

public class DatabaseConnectionTests {

    static DatabaseConnectionManager connectionManager = DatabaseConnectionManager.getInstance();
    static Connection connection = connectionManager.getConnection();

    private static final Logger logger = LogManager.getLogger(DatabaseConnectionTests.class);

    @Test
    @DisplayName("Add new Volunteer to Database without running the browser")
    @Description("This test attempts to add new Volunteer to Database without running the browser.")
    @Severity(CRITICAL)
    public void addVolunteerToDatabaseWithoutRunningBrowser() throws SQLException {

        if (connection != null) {
            System.out.println("Connected to the database. Test addVolunteerToDatabaseWithoutRunningBrowser started.");
        }

        Volunteer user = Instancio.create(Volunteer.getUserModel());

        assertTrue(connectionManager.checkThatUserWithSuchEmailDoNotExistInDatabase(user.getEmail()), "Volunteer with email " + user.getEmail() + " already exists in the database. Impossible to create two users with the same email addresses.");

        String query = "INSERT INTO users (first_name, last_name, email, sex, phone, password, role, status, created_date, updated_date, locale) VALUES ('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getEmail()+"', 'FEMALE', '"+user.getPhoneNumber()+"','"+user.getPassword()+"', 'ROLE_VOLUNTEER', 'ACTIVE', '2024-07-24 11:20:00', '2024-07-24 11:20:00', 'UK')";
        try{
            assert connection != null;
            Statement st = connection.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            System.err.println(ex);
        }

        Allure.step("Insert data about a new volunteer into the database");

        assertTrue(connectionManager.checkThatUserWithSuchEmailAlreadyExistInDatabase(user.getEmail()),"There is no volunteer with email " + user.getEmail() + " in the database. Volunteer insertion failed.");
        logger.info("The new volunteer has been successfully inserted into the database.");
    }

    @Test
    @DisplayName("Get Volunteer from Database without running the browser")
    @Description("This test attempts to get Volunteer data from Database without running the browser, and output the result to the console.")
    @Severity(CRITICAL)
    public void getVolunteerFromDatabaseWithoutRunningBrowser() throws SQLException {

        if (connection != null) {
            System.out.println("Connected to the database. Test getVolunteerFromDatabaseWithoutRunningBrowser started.");
        }

        String query = "SELECT TOP 5 * FROM users ORDER BY id DESC";

        try {
            assert connection != null;
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnName = rsmd.getColumnName(i);
                    String columnValue = rs.getString(i);
                    System.out.print(columnName + " " + columnValue);
                }
                System.out.println("");
            }

            assertTrue(rowCount <= 5, "Expected not more then 5 rows");

            st.close();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Test
    @DisplayName("Login new Volunteer without confirmation of his email in MailHog service")
    @Description("This test attempts to login new Volunteer without confirmation of his email in MailHog service.")
    @Severity(CRITICAL)
    public void loginVolunteerWithoutConfirmationOfEmailInMailHog() throws IOException {
        chromeSetUp();
        SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        objSkarbHomePage.clickOnSelectWebSiteLanguage()
                        .clickOnSelectUkrLanguage()
                        .clickOnCreateNewUser()
                        .clickOnCreateNewVolunteer();

        Volunteer user = Instancio.create(Volunteer.getUserModel());

        logger.info("loginVolunteerWithoutConfirmationOfEmailInMailHog test started. Entering user data.");
        VolunteerRegistrationPage objVolunteerRegistrationPage = new VolunteerRegistrationPage(driver);
        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                    .enterLastName(user.getLastName())
                                    .enterEmail(user.getEmail())
                                    .enterPhoneNumber(user.getPhoneNumber())
                                    .enterPassword(user.getPassword())
                                    .enterConfirmPassword(user.getPassword())
                                    .enterAbout(user.getAbout())
                                    .selectCategory(user.getSelectCategory())
                                    .clickOnSubmitButton();

        objVolunteerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("Registration of user is successful. Message about successful registration is visible.");
        logger.debug("Registration of user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

        connectionManager.confirmUserEmailInDatabase(user.getEmail());

        objSkarbHomePage.clickOnSignInButtonOnHomePage();

        LoginPage objLoginPage = new LoginPage(driver);
        assertAll("Checking the loading of the login page",
                () -> assertTrue(objLoginPage.getLoginInput().isDisplayed(), "loginInput field is invisible"),
                () -> assertTrue(objLoginPage.getPasswordInput().isDisplayed(), "passwordInput field is invisible"),
                () -> assertTrue(objLoginPage.getSignInButtonOnLoginPage().isEnabled(), "signInButton on login page is disable")
        );

        objLoginPage.enterLogin(user.getEmail())
                    .enterPassword(user.getPassword())
                    .clickOnSignInButton();

        VolunteerProfilePage objVolunteerProfilePage = new VolunteerProfilePage(driver);
        assertTrue(objVolunteerProfilePage.getUserProfileButton().isDisplayed(), "User is not logged in");
        logger.info("User logged in successfully.");

        objVolunteerProfilePage.clickOnSignOutButton();
        assertEquals("Ви успішно вийшли з аккаунту.", driver.findElement(By.xpath("//p[@class='text-dark']")) .getText(), "The user is not logged out." );
        logger.info("The user is logged out.");

        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Check Volunteer data in Database after it has been created")
    @Description("This test attempts to create a new Volunteer and then check it data in the database.")
    @Severity(NORMAL)
    public void checkDataInDatabaseAfterNewVolunteerIsCreated() throws IOException {
        chromeSetUp();
        SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        objSkarbHomePage.clickOnSelectWebSiteLanguage()
                        .clickOnSelectUkrLanguage()
                        .clickOnCreateNewUser()
                        .clickOnCreateNewVolunteer();

        Volunteer user = Instancio.create(Volunteer.getUserModel());

        logger.info("Entering user data.");
        VolunteerRegistrationPage objVolunteerRegistrationPage = new VolunteerRegistrationPage(driver);
        objVolunteerRegistrationPage.enterFirstName(user.getFirstName())
                                    .enterLastName(user.getLastName())
                                    .enterEmail(user.getEmail())
                                    .enterPhoneNumber(user.getPhoneNumber())
                                    .enterPassword(user.getPassword())
                                    .enterConfirmPassword(user.getPassword())
                                    .enterAbout(user.getAbout())
                                    .selectCategory(user.getSelectCategory())
                                    .clickOnSubmitButton();

        objVolunteerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("Registration of user is successful. Message about successful registration is visible.");
        logger.debug("Registration of user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

        assertTrue(connectionManager.checkThatUserWithSuchEmailAlreadyExistInDatabase(user.getEmail()),"There is no user with email " + user.getEmail() + " in the database.");
        logger.info("The user is present in the database.");

        connectionManager.checkUserFirstNameInDatabase(user.getEmail(), user.getFirstName());
        connectionManager.checkUserLastNameInDatabase(user.getEmail(), user.getLastName());
        logger.info("The volunteer data in database is correct.");

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Check Partner data in database after it has been created")
    @Description("This test attempts to create a new Partner and then check it data in the database.")
    @Severity(NORMAL)
    public void checkDataInDatabaseAfterNewPartnerIsCreated() throws IOException {
        chromeSetUp();
        SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        objSkarbHomePage.clickOnSelectWebSiteLanguage()
                        .clickOnSelectUkrLanguage()
                        .clickOnCreateNewUser()
                        .clickOnCreateNewPartner();

        Partner user = Instancio.create(Partner.getPartnerModel());

        logger.info("Entering user data.");
        PartnerRegistrationPage objPartnerRegistrationPage = new PartnerRegistrationPage(driver);

        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(user.getGender())));
        objPartnerRegistrationPage.enterFirstName(user.getFirstName())
                                    .enterLastName(user.getLastName())
                                    .enterEmail(user.getEmail())
                                    .enterPhoneNumber(user.getPhoneNumber())
                                    .enterPassword(user.getPassword())
                                    .enterConfirmPassword(user.getPassword())
                                    .enterOrganizationName(user.getOrganizationName())
                                    .selectCategory(user.getSelectCategory())
                                    .enterPosition(user.getPosition())
                                    .enterOrganizationLink(user.getOrganizationLink())
                                    .enterAbout(user.getAbout())
                                    .clickOnSubmitButton();

        objPartnerRegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("Registration of user is successful. Message about successful registration is visible.");
        logger.debug("Registration of user is successful. User email: {}, user password: {}.", user.getEmail(), user.getPassword());

        assertTrue(connectionManager.checkThatUserWithSuchEmailAlreadyExistInDatabase(user.getEmail()),"There is no user with email " + user.getEmail() + " in the database.");
        logger.info("The user is present in the database.");

        connectionManager.checkUserFirstNameInDatabase(user.getEmail(), user.getFirstName());
        connectionManager.checkUserLastNameInDatabase(user.getEmail(), user.getLastName());
        connectionManager.checkUserPositionInOrganizationInDatabase(user.getEmail(),user.getPosition());
        connectionManager.checkPartnerOrganisationNameInDatabase(user.getEmail(),user.getOrganizationName());
        logger.info("The partner data in database is correct.");

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Approve NGO after it has been created")
    @Description("This test attempts to create a new NGO and then approve it in the database.")
    @Severity(NORMAL)
    public void approveNgoInDatabase() throws IOException {
        chromeSetUp();
        SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
        objSkarbHomePage.clickOnSelectWebSiteLanguage()
                .clickOnSelectUkrLanguage()
                .clickOnCreateNewUser()
                .clickOnCreateNewNGO();

        NGO user = Instancio.create(NGO.getNGO_Model());

        NGOforBuilderPattern NGOforBuilderPattern = new NGOforBuilderPattern.Builder()
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setGender(user.getGender())
                .setEmail(user.getEmail())
                .setPhoneNumber(user.getPhoneNumber())
                .setPassword(user.getPassword())
                .setOrganizationName(user.getOrganizationName())
                .setSelectCategory(user.getSelectCategory())
                .setPosition(user.getPosition())
                .setOrganizationLink(user.getOrganizationLink())
                .setAbout(user.getAbout())
                .setRegisterLink(user.getRegisterLink()).build();

        logger.info("Entering user data.");
        NGO_RegistrationPage obgNGO_RegistrationPage = new NGO_RegistrationPage (driver);
        WebElementUtils.clickOnButton(driver.findElement(By.cssSelector(NGOforBuilderPattern.getGender())));
        obgNGO_RegistrationPage.populateNGOregistrationForm(NGOforBuilderPattern.getFirstName(), NGOforBuilderPattern.getLastName(), NGOforBuilderPattern.getEmail(),NGOforBuilderPattern.getPhoneNumber(),NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getOrganizationName(), NGOforBuilderPattern.getSelectCategory(), NGOforBuilderPattern.getPosition(), NGOforBuilderPattern.getOrganizationLink(), NGOforBuilderPattern.getAbout(), NGOforBuilderPattern.getRegisterLink());

        assertTrue(obgNGO_RegistrationPage.verifyNGOregistrationFormPopulatedCorrectly(NGOforBuilderPattern.getFirstName(), NGOforBuilderPattern.getLastName(), NGOforBuilderPattern.getEmail(), NGOforBuilderPattern.getPhoneNumber(), NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getPassword(), NGOforBuilderPattern.getOrganizationName(), NGOforBuilderPattern.getSelectCategory(), NGOforBuilderPattern.getPosition(), NGOforBuilderPattern.getOrganizationLink(), NGOforBuilderPattern.getAbout(), NGOforBuilderPattern.getRegisterLink()), "NGO registration form populated incorrectly");
        obgNGO_RegistrationPage.clickOnSubmitButton();

        obgNGO_RegistrationPage.verifySuccessfulRegistrationMassage();
        logger.info("Registration of user is successful. Message about successful registration is visible.");

        assertTrue(connectionManager.checkThatUserWithSuchEmailAlreadyExistInDatabase(user.getEmail()),"There is no user with email " + user.getEmail() + " in the database.");
        logger.info("The user is present in the database.");

        assertTrue(connectionManager.approveNgoInDatabase(user.getEmail()),"NGO with email " + user.getEmail() + " is not approved in the database.");
        logger.info("The NGO is approved in the database.");

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Check task and set its status in the database after creating")
    @Description("This test attempts to create a new task for volunteer and then check it data in the database. Then to check and set task status.")
    @Severity(NORMAL)
    public void checkTaskInDatabaseAfterCreatingAndSetItStatus() throws IOException {
        chromeSetUp();
        SkarbHomePage objSkarbHomePage = new SkarbHomePage(driver);
        driver.get(utils.ReadPropertiesFileMethod.readHomePageUrlInConfigFile());
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

        assertTrue(objSkarbHomePage.getNavigateToHomePageButton().isEnabled(), "navigateToHomePageButton is disable");
        objSkarbHomePage.clickOnNavigateToHomePageButton();

        assertTrue(objSkarbHomePage.getTasksDropdown().isEnabled(), "tasksDropdown is disable");
        objSkarbHomePage.clickOnTasksDropdown();

        assertTrue(objSkarbHomePage.getCreateTaskForVolunteerButton().isEnabled(), "createTaskForVolunteerButton is disable");
        objSkarbHomePage.clickOnCreateTaskForVolunteerButton();

        assertTrue(driver.getCurrentUrl().contains("tasks/register/volunteer"), "URL should contain text 'tasks/register/volunteer'");

        logger.info("Entering task data.");
        TaskForVolunteer task = Instancio.create(TaskForVolunteer.getTaskForVolunteerModel());
        CreatingNewTaskForVolunteerPage objCreatingNewTaskForVolunteerPage = new CreatingNewTaskForVolunteerPage(driver);
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

        assertTrue(connectionManager.checkThatTaskWithSuchNameExistsInDatabase(task.getTaskName()), "Task with such taskName does not exist in the database");

        connectionManager.checkTaskDataInDatabase(task.getTaskName(), task.getTaskDescription(), task.getExpectedOutcome(),task.getVolunteerBenefit());
        logger.info("Task data in the database is correct.");

        String currentStatus = connectionManager.checkCurrentTaskStatusInDatabase(task.getTaskName());
        logger.info("Current task status in the database - {}",currentStatus);
        Allure.step("Current task status in the database - " + currentStatus + ".");

        String newStatus = "COMPLETED";
        connectionManager.setTaskStatusInDatabase(task.getTaskName(), newStatus);
        logger.info("Set task status in the database to {}",newStatus);

        String currentStatus2 = connectionManager.checkCurrentTaskStatusInDatabase(task.getTaskName());
        logger.info("Current task status in the database - {}",currentStatus2);
        Allure.step("Current task status in the database - " + currentStatus2 + ".");

        WebElement signOutButton = new NGO_ProfilePage(driver).getSignOutButton();
        js.executeScript("arguments[0].scrollIntoView();",signOutButton);
        js.executeScript("arguments[0].click();", signOutButton);
        assertEquals("Ви успішно вийшли з аккаунту.", driver.findElement(By.xpath("//p[@class='text-dark']")) .getText(), "The user is not logged out." );
        logger.info("The user is logged out.");
        Allure.step("The user is logged out.");

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connectionManager.closeConnection();
        if (connection.isClosed()) {
            System.out.println("Connection closed.");
        }
    }
}
