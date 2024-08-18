package testdata;

import models.TaskForVolunteer;
import org.instancio.Instancio;

import java.util.Arrays;
import java.util.List;

public class TaskForVolunteerParameterizedTestData {
    public static List<TaskForVolunteer> getTaskForVolunteerParameterizedTestData() {
        TaskForVolunteer task1 = Instancio.create(TaskForVolunteer.getTaskForVolunteerModel());
        TaskForVolunteer task2 = Instancio.create(TaskForVolunteer.getTaskForVolunteerModel());
        TaskForVolunteer task3 = Instancio.create(TaskForVolunteer.getTaskForVolunteerModel());

        return Arrays.asList(task1, task2, task3);
    }
}
