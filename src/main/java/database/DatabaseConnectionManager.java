package database;

import dev.failsafe.internal.util.Assert;
import io.qameta.allure.Step;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public final class DatabaseConnectionManager {

    private static DatabaseConnectionManager instance;

    private Connection connection;

    private String getDataFromConfigPropertiesFile(String necessaryData) throws IOException {
        utils.ReadPropertiesFileMethod.readProperties("src/test/resources/testdata/config.properties");
        Properties properties = utils.ReadPropertiesFileMethod.getProperties();
        return properties.getProperty(necessaryData);
    }

    private DatabaseConnectionManager(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(getDataFromConfigPropertiesFile("db_connection_string"), getDataFromConfigPropertiesFile("db_username"), getDataFromConfigPropertiesFile("db_password"));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static synchronized DatabaseConnectionManager getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }

    @Step("Confirm user's email in the database")
    public void confirmUserEmailInDatabase(String userEmail){
        if (connection != null) {
            System.out.println("Connected to the database.");
        }

        String query = "UPDATE users SET email_confirmed = 'true' WHERE email = '" + userEmail + "'";

        try{
            assert connection != null;
            Statement st1 = connection.createStatement();
            st1.executeUpdate(query);

            String controlQuery = "SELECT email_confirmed FROM users WHERE email = '" + userEmail + "'";
            Statement st2 = connection.createStatement();
            ResultSet rs  = st2.executeQuery(controlQuery);
            rs.next();
            boolean emailIsConfirmed = rs.getBoolean("email_confirmed");
            if (emailIsConfirmed) {
                System.out.println("User's email is confirmed.");
            } else {System.out.println("User's email is not confirmed!!!");}

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Step("Check that user with such email do not exist in the database")
    public boolean checkThatUserWithSuchEmailDoNotExistInDatabase(String userEmail) {
        boolean result = false;
        String query = "SELECT COUNT (id) AS 'count' FROM users WHERE email = '" + userEmail + "'";
        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int count = rs.getInt("count");
            result = count == 0;
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return result;
    }

    @Step("Check that user with such email already exist in the database")
    public boolean checkThatUserWithSuchEmailAlreadyExistInDatabase(String userEmail) {
        boolean result = false;
        String query = "SELECT COUNT (id) AS 'count' FROM users WHERE email = '" + userEmail + "'";
        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int count = rs.getInt("count");
            result = count == 1;
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return result;
    }

    @Step("Check user's first name in the database")
    public void checkUserFirstNameInDatabase(String userEmail, String firstName){
        String query = "SELECT * FROM users WHERE email = '" + userEmail + "'";

        try{
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String firstNameInDatabase = rs.getString("first_name");
            Assert.isTrue(firstNameInDatabase.equals(firstName), "Users first name in the database is different, but must be the same.");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Step("Check user's last name in the database")
    public void checkUserLastNameInDatabase(String userEmail, String lastName){
        String query = "SELECT * FROM users WHERE email = '" + userEmail + "'";

        try{
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String lastNameInDatabase = rs.getString("last_name");
            Assert.isTrue(lastNameInDatabase.equals(lastName), "Users last name in the database is different, but must be the same");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Step("Check user's position in organization in the database")
    public void checkUserPositionInOrganizationInDatabase(String userEmail, String position){
        String query = "SELECT * FROM users WHERE email = '" + userEmail + "'";

        try{
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String positionInOrganizationInDatabase = rs.getString("position_in_organization");
            Assert.isTrue(positionInOrganizationInDatabase.equals(position), "Users position in organization in the database is different, but must be the same");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Step("Check partner's organization name in the database")
    public void checkPartnerOrganisationNameInDatabase(String userEmail, String organizationName){
        String query = "SELECT name FROM partners JOIN users ON partners.id = users.organization_id WHERE email = '" + userEmail + "'";

        try{
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String organizationNameInDatabase = rs.getString("name");
            Assert.isTrue(organizationNameInDatabase.equals(organizationName), "Users organization name in the database is different, but must be the same");
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Step("Approve NGO in the database")
    public boolean approveNgoInDatabase(String userEmail){
        String query = "UPDATE ngos SET confirmed = 'true' FROM ngos JOIN users ON ngos.id = users.organization_id WHERE email = '" + userEmail + "'";
        boolean ngoIsApproved = false;
        try{
            assert connection != null;
            Statement st1 = connection.createStatement();
            st1.executeUpdate(query);

            String controlQuery = "SELECT confirmed FROM ngos JOIN users ON ngos.id = users.organization_id WHERE email = '" + userEmail + "'";
            Statement st2 = connection.createStatement();
            ResultSet rs  = st2.executeQuery(controlQuery);
            rs.next();
            ngoIsApproved = rs.getBoolean("confirmed");
            if (ngoIsApproved) {
                System.out.println("NGO is approved in the database.");
            } else {System.out.println("NGO is not approved in the database!!!");}

        } catch (Exception ex) {
            System.err.println(ex);
        }
        return ngoIsApproved;
    }

    @Step("Check that task with such taskName exists in the database")
    public boolean checkThatTaskWithSuchNameExistsInDatabase(String taskName) {
        boolean result = false;
        String query = "SELECT COUNT (id) AS 'count' FROM volunteer_tasks WHERE name = '" + taskName + "'";
        try {
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int count = rs.getInt("count");
            result = count == 1;
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return result;
    }

    @Step("Check task data in the database")
    public void checkTaskDataInDatabase(String taskName, String taskDescription, String expectedOutcome, String volunteerBenefit){
        String query = "SELECT * FROM volunteer_tasks WHERE name = '" + taskName + "'";

        try{
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();

            String taskDescriptionInDatabase = rs.getString("description");
            Assert.isTrue(taskDescriptionInDatabase.equals(taskDescription), "Task description in the database is different, but must be the same");

            String expectedOutcomeInDatabase = rs.getString("expected_outcome");
            Assert.isTrue(expectedOutcomeInDatabase.equals(expectedOutcome), "Expected outcome in the database is different, but must be the same");

            String volunteerBenefitInDatabase = rs.getString("benefit");
            Assert.isTrue(volunteerBenefitInDatabase.equals(volunteerBenefit), "Volunteer benefit in the database is different, but must be the same");

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @Step("Check current task status in the database")
    public String checkCurrentTaskStatusInDatabase(String taskName){
        String query = "SELECT status FROM volunteer_tasks WHERE name = '" + taskName + "'";
        String statusInDatabase = "";

        try{
            assert connection != null;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            statusInDatabase = rs.getString("status");
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return statusInDatabase;
    }

    @Step("Set task status in the database")
    public void setTaskStatusInDatabase(String taskName, String newStatus){
        String query = "UPDATE volunteer_tasks SET status = '" + newStatus +"' WHERE name = '" + taskName + "'";
        try{
            assert connection != null;
            Statement st1 = connection.createStatement();
            st1.executeUpdate(query);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}

