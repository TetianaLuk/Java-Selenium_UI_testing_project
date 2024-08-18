package models;

import org.instancio.Instancio;
import org.instancio.Model;

import static org.instancio.Select.field;

public class Volunteer {

    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;
    String about;
    String selectCategory;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAbout() {
        return about;
    }

    public String getSelectCategory() {
        return selectCategory;
    }

    static Model<Volunteer> userModel = Instancio.of(Volunteer.class)
            .generate(field(Volunteer::getFirstName), gen -> gen.text().pattern("#C#c#c#c#c"))
            .generate(field(Volunteer::getLastName), gen -> gen.text().pattern("#C#c#c#c#c"))
            .generate(field(Volunteer::getEmail), gen -> gen.text().pattern("#c#c#c#c@example.com"))
            .generate(field(Volunteer::getPhoneNumber), gen -> gen.text().pattern("+38063#d#d#d#d#d#d#d"))
            .generate(field(Volunteer::getPassword), gen -> gen.text().pattern("#C#C#c#c@@#d#a#a#a#a#a"))
            .generate(field(Volunteer::getAbout), gen -> gen.text().pattern("#a#a#a#a#a#a#a#a"))
            .generate(field(Volunteer::getSelectCategory), gen -> gen.oneOf("Graphic design", "Logo design", "Website design", "UI-UX", "Programming", "Soft drinks", "Sporting equipment", "Sporting clothes", "Sales", "Brand promotion"))
            .toModel();

    public static Model<Volunteer> getUserModel() {
        return userModel;
    }

}


