package main.java.pl.ffWorkApp.cli;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CommandParser {
    private final Scanner scanner;

    public CommandParser(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readCommand () {
        return scanner.nextLine();
    }

    public String[] parse(String command) {
        return command.trim().split("\\s+");
    }

    public LocalDateTime parseDateTime(String value) {
        return LocalDateTime.parse(value);
    }

    public void close() {
        scanner.close();
    }
}