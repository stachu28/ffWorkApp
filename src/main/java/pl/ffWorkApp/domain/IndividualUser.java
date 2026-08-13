package main.java.pl.ffWorkApp.domain;

public class IndividualUser extends User {
    private boolean isStudent;
    private long studentIt;
    public IndividualUser(String email, String displayName, String phoneNumber) {
        super(email, displayName, phoneNumber);
        this.isStudent = false;
    }

    public IndividualUser(String email, String displayName, String phoneNumber, long studentIt) {
        super(email, displayName, phoneNumber);
        this.isStudent = true;
        this.studentIt = studentIt;
    }
}