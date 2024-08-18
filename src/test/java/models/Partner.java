package models;

import org.instancio.Instancio;
import org.instancio.Model;

import static org.instancio.Select.field;

public class Partner {

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

    static Model<Partner> partnerModel = Instancio.of(Partner.class)
            .generate(field(Partner::getFirstName), gen -> gen.text().pattern("#C#c#c#c#c"))
            .generate(field(Partner::getLastName), gen -> gen.text().pattern("#C#c#c#c#c"))
            .generate(field(Partner::getGender), gen -> gen.oneOf("#male", "#female"))
            .generate(field(Partner::getEmail), gen -> gen.text().pattern("#c#c#c#c#c#c#c#c@skarb.ngo"))
            .generate(field(Partner::getPhoneNumber), gen -> gen.text().pattern("+38063#d#d#d#d#d#d#d"))
            .generate(field(Partner::getPassword), gen -> gen.text().pattern("#C#C#c#c@@#d#a#a#a#a#a"))
            .generate(field(Partner::getOrganizationName), gen -> gen.text().pattern("#C#c#c#c#c#c#c#c#c"))
            .generate(field(Partner::getSelectCategory), gen -> gen.oneOf("Graphic design", "Logo design", "Website design", "UI-UX", "Programming", "Soft drinks", "Sporting equipment", "Sporting clothes", "Sales", "Brand promotion"))
            .generate(field(Partner::getPosition), gen -> gen.text().pattern("#C#c#c#c#c#c#c#c"))
            .generate(field(Partner::getOrganizationLink), gen -> gen.text().pattern("#c#c#c#c#c#c#c.ngo"))
            .generate(field(Partner::getAbout), gen -> gen.text().pattern("#a#a#a#a#a#a#a#a"))
            .toModel();

    public static Model<Partner> getPartnerModel() {
        return partnerModel;
    }

}
