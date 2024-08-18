package testdata;

import models.Volunteer;
import org.instancio.Instancio;

import java.util.Arrays;
import java.util.List;

public class VolunteerParameterizedTestData {

    public static List<Volunteer> getVolunteerParameterizedTestData() {
        Volunteer user1 = Instancio.create(Volunteer.getUserModel());
        Volunteer user2 = Instancio.create(Volunteer.getUserModel());
        Volunteer user3 = Instancio.create(Volunteer.getUserModel());

        return Arrays.asList(user1, user2, user3);
    }
}
