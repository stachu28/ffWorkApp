package main.java.pl.ffWorkApp.domain;

abstract public class User {
    String email;
    String displayName;
    long userID;
    private static long nextId = 1;

    public User(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
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
    public long getUserID() {
        return userID;
    }
}