package main.java.pl.ffWorkApp.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Wybrałem LocalDateTime bo pozwala przechowywac date i godzine oraz udostepnia potrzebne operacje (parse, porownywanie,
// dodawanie minut bez koniecznosci tworzenia wlasnej klasy i implementowania tych operacji samemu. Nie przechowuje
// strefy czasowej ale z opisu zadania wnioskuje, że nie jest to wymagane.

public final class TimeUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private TimeUtils() {
    }

    public static LocalDateTime parse(String time) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("Date cannot be left empty!");
        }
        try {
            return LocalDateTime.parse(time.trim(), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Illegal time value entered!\n" +
                    "entered: " + time.trim() + ", expected: " + DATE_TIME_FORMATTER);
        }
    }

    public static String format(LocalDateTime time) {
        return time.format(DATE_TIME_FORMATTER);
    }

    public static String timeBetween(LocalDateTime start, LocalDateTime end) {
        long timeBetweenInMinutes = Duration.between(start, end).toMinutes();
        long hours = timeBetweenInMinutes / 60;
        long minutes = timeBetweenInMinutes % 60;

        return hours + "h, " + minutes + "min.";
    }
}