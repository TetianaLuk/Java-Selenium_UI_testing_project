# 📦 Java-Selenium UI testing project 
This project is a study Maven Java application demonstrating automated testing practice using following stack of technologies: Selenium WebDriver, JUnit, MS SQL server, Allure. 
It is designed to showcase skills in writing maintainable and efficient automated tests.

## 🌟 Features
- Page Object Model (POM) using Page Factory: implements POM design pattern using Page Factory to organize test code and improve tests maintainability by separating test objects from test scripts.
- Method Chaining: implements Method Chaining in POM to make test steps more readable.
- Log4j2: implements Log4j2 logging framework to perform application logging, it provides precise information about a run of the application. 
- Instancio Models: utilizes Instancio to generate dynamic test data.
- Allure Reports: integrated Allure for generating detailed and visually appealing test reports.

## 🚧 Prerequisites
Before you can run this project, you must have the following software installed on your computer:
- Java Development Kit (this application has been written in JDK 17)
- Maven (this application has been written using Maven version 3.9.6). Maven is required to manage project dependencies and build the project.

## 🛠️ Installation
1. Clone this repository to your local machine.   
   ```sh
   git clone https://github.com/TetianaLuk/Java-Selenium_UI_testing_project.git
   ```
2. Navigate to the project directory using the command line.
3. Install the dependencies.   
   ```sh
   mvn clean install
   ```

## 🌐 Website under test
* https://skarb.foxminded.ua <br/>

Note that this website is being used for testing purposes, and I, the tester, acknowledge that I do not own or have any rights to this website. 
Testing activities are for demo purposes only.

## 🚀Usage
### Running Tests
Navigate to the project directory using the command line. 
To execute all test classes, run command:
   ```sh
   mvn clean test 
   ```  
To execute one test class run the following command but replace `<test_class>` with the name of the test class:
   ```sh
   mvn test -Dtest=<test_class>
   ```  
### Generating Allure Report

You can generate a report using one of the following commands:
  ```sh
   mvn allure:serve
   ```  
Report will be generated into temp folder. Web server with results will start.
  ```sh
   mvn allure:report
   ```  
Report will be generated to directory: target/site/allure-maven/index.html
## 📦 Test classes 
This project contains 13 test classes (test suits):
- `VolunteerRegistrationFormPositiveTests`: suit of positive tests for Volunteer registration form.
- `VolunteerRegistrationFormNegativeTests`: suit of negative tests for Volunteer registration form.
- `VolunteerParameterizedTest`: parameterized test for Volunteer registration form with use of valid data from @MethodSource.
- `VolunteerInMailHogServiceTest`: test attempts to register new Volunteer and then confirm his email in the MailHog service.
- `PartnerRegistrationFormPositiveTests`: suit of positive tests for Partner registration form.
- `PartnerRegistrationFormNegativeTests`: suit of negative tests for Partner registration form.
- `PartnerParameterizedTest`: parameterized test for Partner registration form with use of valid data from @MethodSource.
- `PartnerInMailHogServiceTest`: test attempts to register new Partner and then confirm his email in the MailHog service.
- `NGOregistrationFormPositiveTest`: positive test for NGO registration form (form populated using Builder pattern).  
- `NGOtasksForVolunteerParameterizedTest`: tests attempt to login in existing NGO profile and create new tasks for volunteer using valid data from @MethodSource and @CsvFileSource.
- `DatabaseConnectionTests`: suit of positive tests which are interacting with data in the Database using SQL queries. 
- `ChromeTest`: simple test that attempts to open Chrome browser and go to specified url.
- `FirefoxTest`: simple test that attempts to open Firefox browser and go to specified url.

## 📦 Project structure 
- src/main/java: 
  - database: Database connection manager class.
  - pageobjects: Page Object classes implementing the POM with Page Factory.
  - utils: classes for driver configuration and setup, random data generation, read properties methods, methods for interaction with web elements.
- src/main/resources: log4j2.xml configuration file.
- src/test/java: 
  - models: classes with Instancio models for fake test data generation.
  - testdata: classes with test data for parameterized tests.
  - tests: test classes.
- src/test/resources:
  - testdata: config.properties file, dataForTasksForVolunteer.csv file.
  - allure.properties configuration file.
- gitignore file.
- pom.xml Maven configuration file.

## 🌟 Contact
Please contact bobrotetiana@gmail.com if you have any questions.
