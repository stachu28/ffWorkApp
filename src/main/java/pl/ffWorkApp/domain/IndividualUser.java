package main.java.pl.ffWorkApp.domain;

public class IndividualUser extends User {
    private boolean isStudent;
    private long studentId;

    public IndividualUser(String email, String displayName) {
        super(email, displayName);
        this.isStudent = false;
    }

    public IndividualUser(String email, String displayName, long studentId) {
        super(email, displayName);
        this.isStudent = true;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "UserID: " + userID + ", name: " + displayName + ", email: " + email + ", student: "
                + (isStudent ? "yes, student id: " + studentId : "no");
    }
}