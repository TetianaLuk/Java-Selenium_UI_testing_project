package models;

import org.instancio.Instancio;
import org.instancio.Model;

import static org.instancio.Select.field;

public class TaskForVolunteer {

    String taskName;
    String selectCategory;
    String taskDescription;
    String expectedOutcome;
    String volunteerBenefit;
    String requirementsToVolunteer;
    String savedMoney;
    String firstWorkStageName;
    String firstWorkStageDuration;
    String firstWorkStageDescription;
    String secondWorkStageName;
    String secondWorkStageDuration;
    String secondWorkStageDescription;

    public String getTaskName() {
        return taskName;
    }

    public String getSelectCategory() {
        return selectCategory;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getExpectedOutcome() {
        return expectedOutcome;
    }

    public String getVolunteerBenefit() {
        return volunteerBenefit;
    }

    public String getRequirementsToVolunteer() {
        return requirementsToVolunteer;
    }

    public String getSavedMoney() {
        return savedMoney;
    }

    public String getFirstWorkStageName() {
        return firstWorkStageName;
    }

    public String getFirstWorkStageDuration() {
        return firstWorkStageDuration;
    }

    public String getFirstWorkStageDescription() {
        return firstWorkStageDescription;
    }

    public String getSecondWorkStageName() {
        return secondWorkStageName;
    }

    public String getSecondWorkStageDuration() {
        return secondWorkStageDuration;
    }

    public String getSecondWorkStageDescription() {
        return secondWorkStageDescription;
    }

    static Model<TaskForVolunteer> TaskForVolunteerModel = Instancio.of(TaskForVolunteer.class)
            .generate(field(TaskForVolunteer::getTaskName), gen -> gen.text().pattern("Task #C#c#c#c#c"))
            .generate(field(TaskForVolunteer::getSelectCategory), gen -> gen.oneOf("Graphic design", "Logo design", "Website design", "UI-UX", "Programming", "Soft drinks", "Sporting equipment", "Sporting clothes", "Sales", "Brand promotion"))
            .generate(field(TaskForVolunteer::getTaskDescription), gen -> gen.text().pattern("Task description: #c#c#c#c#c#c#c#c#c#c#c"))
            .generate(field(TaskForVolunteer::getExpectedOutcome), gen -> gen.text().pattern("Expected outcome: #c#c#c#c#c#c#c#c#c#c#c"))
            .generate(field(TaskForVolunteer::getVolunteerBenefit), gen -> gen.oneOf("recommendation letter", "monetary reward"))
            .generate(field(TaskForVolunteer::getRequirementsToVolunteer), gen -> gen.oneOf("experience", "portfolio"))
            .generate(field(TaskForVolunteer::getSavedMoney), gen -> gen.text().pattern("3#d#d"))
            .generate(field(TaskForVolunteer::getFirstWorkStageName), gen -> gen.text().pattern("Name of the first work stage: #c#c#c#c#c#c#c#c#c#c#c"))
            .generate(field(TaskForVolunteer::getFirstWorkStageDuration), gen -> gen.oneOf("1", "2"))
            .generate(field(TaskForVolunteer::getFirstWorkStageDescription), gen -> gen.text().pattern("Description of the first work stage: #c#c#c#c#c#c#c#c#c#c#c"))
            .generate(field(TaskForVolunteer::getSecondWorkStageName), gen -> gen.text().pattern("Name of the second work stage: #c#c#c#c#c#c#c#c#c#c#c"))
            .generate(field(TaskForVolunteer::getSecondWorkStageDuration), gen -> gen.oneOf("1", "2", "3"))
            .generate(field(TaskForVolunteer::getSecondWorkStageDescription), gen -> gen.text().pattern("Description of the second work stage: #c#c#c#c#c#c#c#c#c#c#c"))
            .toModel();

    public static Model<TaskForVolunteer> getTaskForVolunteerModel() {
        return TaskForVolunteerModel;
    }



}
