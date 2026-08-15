package main.java.pl.ffWorkApp.domain;

abstract public class User {
    String email;
    String displayName;
    String phoneNumber;
    long userID;
    private static long nextId = 1;

    public User(String email, String displayName, String phoneNumber) {
        this.email = email;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.userID = nextId++;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email Incorrect!");
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getUserID() {
        return userID;
    }
}