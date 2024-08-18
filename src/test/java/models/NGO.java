package models;

import org.instancio.Instancio;
import org.instancio.Model;

import static org.instancio.Select.field;

public class NGO {

    String firstName;
    String lastName;
    String gender;
    String email;
    String phoneNumber;
    String password;
    String organizationName;
    String selectCategory;
    String position;
    String organizationLink;
    String about;
    String registerLink;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
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

    public String getOrganizationName() {
        return organizationName;
    }

    public String getSelectCategory() {
        return selectCategory;
    }

    public String getPosition() {
        return position;
    }

    public String getOrganizationLink() {
        return organizationLink;
    }

    public String getAbout() {
        return about;
    }

    public String getRegisterLink() {
        return registerLink;
    }

    static Model<NGO> NGO_Model = Instancio.of(NGO.class)
            .generate(field(NGO::getFirstName), gen -> gen.text().pattern("#C#c#c#c#c"))
            .generate(field(NGO::getLastName), gen -> gen.text().pattern("#C#c#c#c#c"))
            .generate(field(NGO::getGender), gen -> gen.oneOf("#male", "#female"))
            .generate(field(NGO::getEmail), gen -> gen.text().pattern("#c#c#c#c#c#c#c#c@example.com"))
            .generate(field(NGO::getPhoneNumber), gen -> gen.text().pattern("+38050#d#d#d#d#d#d#d"))
            .generate(field(NGO::getPassword), gen -> gen.text().pattern("#C#C#c#c@@#d#a#a#a#a#a"))
            .generate(field(NGO::getOrganizationName), gen -> gen.text().pattern("#C#c#c#c#c#c#c#c#c"))
            .generate(field(NGO::getSelectCategory), gen -> gen.oneOf("Graphic design", "Logo design", "Website design", "UI-UX", "Programming", "Soft drinks", "Sporting equipment", "Sporting clothes", "Sales", "Brand promotion"))
            .generate(field(NGO::getPosition), gen -> gen.text().pattern("#C#c#c#c#c#c#c#c"))
            .generate(field(NGO::getOrganizationLink), gen -> gen.text().pattern("#c#c#c#c#c#c#c.org"))
            .generate(field(NGO::getAbout), gen -> gen.text().pattern("#a#a#a#a#a#a#a#a"))
            .generate(field(NGO::getRegisterLink), gen -> gen.text().pattern("https://usr.minjust.gov.ua/content/free-search/#c#c#c#c#c#c#c"))
            .toModel();

    public static Model<NGO> getNGO_Model() {
        return NGO_Model;
    }
}
