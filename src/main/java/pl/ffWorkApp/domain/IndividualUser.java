package main.java.pl.ffWorkApp.domain;

public class IndividualUser extends User {
    private boolean isStudent;
    private long studentId;

    public IndividualUser(String email, String displayName, String phoneNumber) {
        super(email, displayName, phoneNumber);
        this.isStudent = false;
    }

    public IndividualUser(String email, String displayName, String phoneNumber, long studentId) {
        super(email, displayName, phoneNumber);
        this.isStudent = true;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "UserID: " + userID + ", name: " + displayName + ", email: " + email + ", phone number: " + phoneNumber +
                ", student: " + (isStudent ? "yes, student id: " + studentId : "no");
    }
}