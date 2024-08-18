package models;

public class VolunteerForBuilderPattern {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String about;
    private final String selectCategory;

    public static class Builder{
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String password;
        private String about;
        private String selectCategory;

        public Builder(){}

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAbout(String about) {
            this.about = about;
            return this;
        }

        public Builder setSelectCategory(String selectCategory) {
            this.selectCategory = selectCategory;
            return this;
        }

        public VolunteerForBuilderPattern build() {
            return new VolunteerForBuilderPattern(this);
        }
    }

    private VolunteerForBuilderPattern(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.password = builder.password;
        this.about = builder.about;
        this.selectCategory = builder.selectCategory;
    }

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
}
