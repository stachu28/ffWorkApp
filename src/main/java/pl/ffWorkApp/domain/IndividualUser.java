package main.java.pl.ffWorkApp.domain;

public class IndividualUser extends User {
    private boolean isStudent;
    private long studentI;

    public IndividualUser(String email, String displayName, String phoneNumber) {
        super(email, displayName, phoneNumber);
        this.isStudent = false;
    }

    public IndividualUser(String email, String displayName, String phoneNumber, long studentIt) {
        super(email, displayName, phoneNumber);
        this.isStudent = true;
        this.studentI = studentIt;
    }

    @Override
    public String toString() {
        return "UserID: " + userID + ", name: " + displayName + ", email: " + email + ", phone number: " + phoneNumber +
                ", student: " + (isStudent ? "yes, student id: " + studentI : "no");
    }
}