package main.java.pl.ffWorkApp.domain;

public class CompanyUser extends User {
    private String companyName;
    private String taxId;

    public CompanyUser(String email, String displayName, String companyName, String taxId) {
        super(email, displayName);
        this.companyName = companyName;
        this.taxId = taxId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTaxId() {
        return taxId;
    }

    @Override
    public String toString() {
        return "UserID: " + userID + ", name: " + displayName + ", email: " + email +
                ", company: " + companyName + ", tax ID: " + taxId;
    }
}