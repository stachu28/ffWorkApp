package main.java.pl.ffWorkApp;

import main.java.pl.ffWorkApp.time.TimeUtils;

import java.time.LocalDateTime;

public class Main {
    static void main(String[] args) {
        LocalDateTime departure = TimeUtils.parse("2026-08-10 12:00");
        LocalDateTime arrival = TimeUtils.parse("2026-08-10 17:00");
        System.out.println(TimeUtils.timeBetween(departure, arrival));
        LocalDateTime lateArrival = arrival.plusHours(2).plusMinutes(24);
        System.out.println(TimeUtils.timeBetween(departure, lateArrival));
    }
}