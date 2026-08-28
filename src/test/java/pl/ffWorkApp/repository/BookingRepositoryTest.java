package test.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.*;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryBookingRepository;

import java.time.LocalDateTime;
import java.util.Set;

public class BookingRepositoryTest {
    static void main(String[] args) {
        InMemoryBookingRepository testRepository = new InMemoryBookingRepository();
        IndividualUser testUser = new IndividualUser("test@user.com", "Steve McQueen");
        Room testRoom = new Room("Test Room", 4, Set.of("whiteboard"));
        Booking testBooking = new Booking(testUser, testRoom, LocalDateTime.of(2026, 1, 1, 12, 0),
                LocalDateTime.of(2026, 1, 1, 13, 0));
        testRepository.add(testBooking);
        System.out.println("\n* Find By BookingID Test *");
        System.out.println(testRepository.findById(testBooking.getId()).orElse(testBooking));

        System.out.println("\n* Find by Resource Test *");
        for (Booking booking : testRepository.findByResource(testRoom)) {
            System.out.println("BookingID: " + booking.getId());
        }

        System.out.println("\n* Find by User Test *");
        for (Booking booking : testRepository.findByUser(testUser)) {
            System.out.println("BookingID: " + booking.getId());
        }

        System.out.println("\n* Find All Test *");
        Booking testBooking2 = new Booking(testUser, testRoom, LocalDateTime.of(2026, 1, 2, 12, 0),
                LocalDateTime.of(2026, 1, 2, 13, 0));
        Booking testBooking3 = new Booking(testUser, testRoom, LocalDateTime.of(2026, 1, 3, 12, 0),
                LocalDateTime.of(2026, 1, 3, 13, 0));
        testRepository.add(testBooking2);
        testRepository.add(testBooking3);
        for (Booking booking : testRepository.findAll()) {
            System.out.println("BookingID: " + booking.getId());
        }

        System.out.println("\n* Duplicate Booking Test *");
        try {
            testRepository.add(testBooking);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}