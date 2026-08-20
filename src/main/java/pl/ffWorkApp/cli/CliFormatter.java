package main.java.pl.ffWorkApp.cli;

public class CliFormatter {
    public void ok(String massage) {
        System.out.println("OK: " + massage);
    }

    public void error(String massage) { System.out.println("ERROR: " + massage); }
}