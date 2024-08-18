package models;

public class NGOforBuilderPattern {

    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String email;
    private final String phoneNumber;
    private final String password;
    private final String organizationName;
    private final String selectCategory;
    private final String position;
    private final String organizationLink;
    private final String about;
    private final String registerLink;

    public static class Builder {
        private String firstName;
        private String lastName;
        private String gender;
        private String email;
        private String phoneNumber;
        private String password;
        private String organizationName;
        private String selectCategory;
        private String position;
        private String organizationLink;
        private String about;
        private String registerLink;

        public Builder() {}

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
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

        public Builder setOrganizationName(String organizationName) {
            this.organizationName = organizationName;
            return this;
        }

        public Builder setSelectCategory(String selectCategory) {
            this.selectCategory = selectCategory;
            return this;
        }

        public Builder setPosition(String position) {
            this.position = position;
            return this;
        }

        public Builder setOrganizationLink(String organizationLink) {
            this.organizationLink = organizationLink;
            return this;
        }

        public Builder setAbout(String about) {
            this.about = about;
            return this;
        }

        public Builder setRegisterLink(String registerLink) {
            this.registerLink = registerLink;
            return this;
        }

        public NGOforBuilderPattern build(){
            return new NGOforBuilderPattern(this);
        }
    }

    private NGOforBuilderPattern(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.gender = builder.gender;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.password = builder.password;
        this.organizationName = builder.organizationName;
        this.selectCategory = builder.selectCategory;
        this.position = builder.position;
        this.organizationLink = builder.organizationLink;
        this.about = builder.about;
        this.registerLink = builder.registerLink;
    }

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

}
