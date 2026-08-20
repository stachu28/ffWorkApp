package test.java.pl.ffWorkApp.service;

import main.java.pl.ffWorkApp.domain.*;
import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.pricing.StandardPricing;
import main.java.pl.ffWorkApp.repository.BookingRepository;
import main.java.pl.ffWorkApp.repository.ResourceRepository;
import main.java.pl.ffWorkApp.repository.UserRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryBookingRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryResourceRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryUserRepository;
import main.java.pl.ffWorkApp.service.BookingService;

import java.time.LocalDateTime;
import java.util.Set;

public class BookingServiceTest {
    static void main(String[] args) {
        UserRepository testUserRepository = new InMemoryUserRepository();
        ResourceRepository testResourceRepository = new InMemoryResourceRepository();
        BookingRepository testBookingRepository = new InMemoryBookingRepository();

        BookingService testBookingService = new BookingService(testUserRepository, testResourceRepository, testBookingRepository, new StandardPricing());

        User user = new main.java.pl.ffWorkApp.domain.IndividualUser("test@user.com", "Anna Nowak", "+48 222 333 444");

        Room room = new Room("Test Room", Money.of("80"), 12, Set.of("projector", "whiteboard"));

        Desk desk = new Desk("Test Desk", DeskType.HOT, Money.of("25"));

        Device device = new Device("projector", 2, Money.of("40"));

        testUserRepository.add(user);
        testResourceRepository.add(room);
        testResourceRepository.add(desk);
        testResourceRepository.add(device);

        System.out.println("\n* Creating Booking Test *");
        Booking roomBooking = testBookingService.book(user, room, LocalDateTime.of(2026, 8, 10, 10, 0),
                LocalDateTime.of(2026, 8, 10, 12, 0));
        System.out.println(roomBooking);

        System.out.println("\n* Overloading Test *");
        Booking deskBooking = testBookingService.book(user, desk, LocalDateTime.of(2026, 8, 10, 10, 0), 90);
        System.out.println(deskBooking);
        System.out.println("Duration: " + deskBooking.durationMinutes() + " minutes");

        System.out.println("\n* Room Test *");
        System.out.println(room.describe());
        System.out.println("Hourly rate: " + room.hourlyRate());

        System.out.println("\n* Desk Test *");
        System.out.println(desk.describe());
        System.out.println("Hourly rate: " + desk.hourlyRate());

        System.out.println("\n* Device Test *");
        System.out.println(device.describe());
        System.out.println("Hourly rate: " + device.hourlyRate());

        System.out.println("\n* Collision Test *");
        try {
            testBookingService.book(user, room, LocalDateTime.of(2026, 8, 10, 11, 0),
                    LocalDateTime.of(2026, 8, 10, 13, 0));
            System.out.println("ERROR: collision was not detected");
        } catch (IllegalArgumentException e) {
            System.out.println("Collision detected: " + e.getMessage());
        }

        System.out.println("\n* Device Quantity Test *");
        LocalDateTime deviceStart = LocalDateTime.of(2026, 8, 10, 14, 0);
        LocalDateTime deviceEnd = LocalDateTime.of(2026, 8, 10, 16, 0);
        Booking deviceBooking1 = testBookingService.book(user, device, deviceStart, deviceEnd);
        System.out.println("First device booking: " + deviceBooking1.getId());
        Booking deviceBooking2 = testBookingService.book(user, device, deviceStart, deviceEnd);
        System.out.println("Second device booking: " + deviceBooking2.getId());
        try {
            testBookingService.book(user, device, deviceStart, deviceEnd);
            System.out.println("ERROR: third device booking should fail");
        } catch (IllegalArgumentException e) {
            System.out.println("Quantity limit reached: " + e.getMessage());
        }

        System.out.println("\n* Confirming Booking Test *");
        Booking confirmBooking = testBookingService.book(user, room, LocalDateTime.of(2026, 8, 11, 10, 0),
                LocalDateTime.of(2026, 8, 11, 11, 0));
        System.out.println("Before confirmation: " + confirmBooking.getStatus());
        confirmBooking.confirm();
        System.out.println("After confirmation: " + confirmBooking.getStatus());

        System.out.println("\n* Cancel Test *");
        Booking cancelBooking = testBookingService.book(user, room, LocalDateTime.of(2026, 8, 11, 12, 0),
                LocalDateTime.of(2026, 8, 11, 13, 0));
        System.out.println("Before cancel: " + cancelBooking.getStatus());
        testBookingService.cancel(cancelBooking.getId());
        System.out.println("After cancel: " + cancelBooking.getStatus());

        System.out.println("\n* Complete Test *");
        System.out.println("Before complete: " + confirmBooking.getStatus());
        confirmBooking.complete();
        System.out.println("After complete: " + confirmBooking.getStatus());
    }
}