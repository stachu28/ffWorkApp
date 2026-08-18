package test.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.domain.IndividualUser;
import main.java.pl.ffWorkApp.domain.Room;
import main.java.pl.ffWorkApp.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

public class BookingTest {
    public static void main(String[] args) {
        System.out.println("* Booking Creation Test *");
        User user = new IndividualUser("test@example.com", "Test User", "+48222333444");
        Room room = new Room("Test Room", 10, Set.of("projector"));
        LocalDateTime start = LocalDateTime.of(2025, 9, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 9, 15, 12, 30);
        Booking booking = new Booking(user, room, start, end);
        System.out.println("ID: " + booking.getId());
        System.out.println("User: " + booking.getUser());
        System.out.println("Resource: " + booking.getResource().getName());
        System.out.println("Start: " + booking.getStart());
        System.out.println("End: " + booking.getEnd());
        System.out.println("Status: " + booking.getStatus());

        System.out.println("\n* Duration Test *");
        System.out.println("Expected: 150 minutes");
        System.out.println("Actual: " + booking.durationMinutes() + " minutes");

        System.out.println("\n* Valid Status Transitions Test *");
        System.out.println("Initial status: " + booking.getStatus());
        booking.confirm();
        System.out.println("After confirm: " + booking.getStatus());
        booking.complete();
        System.out.println("After complete: " + booking.getStatus());

        System.out.println("\n* PENDING -> CANCELLED Test *");
        Booking cancelledBooking = new Booking(user, room, LocalDateTime.of(2025, 9, 16, 10, 0),
                LocalDateTime.of(2025, 9, 16, 11, 0));
        System.out.println("Initial status: " + cancelledBooking.getStatus());
        cancelledBooking.cancel();
        System.out.println("After cancel: " + cancelledBooking.getStatus());

        System.out.println("\n* CONFIRMED -> CANCELLED Test *");
        Booking confirmedBooking = new Booking(user, room, LocalDateTime.of(2025, 9, 17, 10, 0),
                LocalDateTime.of(2025, 9, 17, 11, 0));
        confirmedBooking.confirm();
        System.out.println("After confirm: " + confirmedBooking.getStatus());
        confirmedBooking.cancel();
        System.out.println("After cancel: " + confirmedBooking.getStatus());

        System.out.println("\n* Invalid Status Transitions Test *");
        try {
            booking.cancel();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        try {
            booking.confirm();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        Booking pendingBooking = new Booking(user, room, LocalDateTime.of(2025, 9, 18, 10, 0),
                LocalDateTime.of(2025, 9, 18, 11, 0));
        try {
            pendingBooking.complete();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}