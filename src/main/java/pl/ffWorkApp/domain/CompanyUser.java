package main.java.pl.ffWorkApp.domain;

public class CompanyUser extends User {
    private String companyName;
    private String taxId;

    public CompanyUser(String email, String displayName, String phoneNumber, String companyName, String taxId) {
        super(email, displayName, phoneNumber);
        this.companyName = companyName;
        this.taxId = taxId;
    }

    @Override
    public String toString() {
        return "User ID: " + userID + ", name: " + displayName + ", email: " + email + ", phone number: " + phoneNumber +
                ", company: " + companyName + ", tax ID: " + taxId;
    }
}