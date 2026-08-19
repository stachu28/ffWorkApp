package test.java.pl.ffWorkApp.service;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.domain.IndividualUser;
import main.java.pl.ffWorkApp.domain.Room;
import main.java.pl.ffWorkApp.domain.User;
import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.payment.Payment;
import main.java.pl.ffWorkApp.pricing.StandardPricing;
import main.java.pl.ffWorkApp.repository.BookingRepository;
import main.java.pl.ffWorkApp.repository.ResourceRepository;
import main.java.pl.ffWorkApp.repository.UserRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryBookingRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryResourceRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryUserRepository;
import main.java.pl.ffWorkApp.service.BookingService;
import main.java.pl.ffWorkApp.service.PaymentService;

import java.time.LocalDateTime;
import java.util.Set;

public class PaymentServiceTest {
    static void main(String[] args) {
        UserRepository userRepository = new InMemoryUserRepository();
        ResourceRepository resourceRepository = new InMemoryResourceRepository();
        BookingRepository bookingRepository = new InMemoryBookingRepository();
        BookingService bookingService = new BookingService(userRepository, resourceRepository, bookingRepository,
                new StandardPricing());
        PaymentService paymentService = new PaymentService(bookingRepository);
        User user = new IndividualUser("test@user.com", "John Nowak", "+48 222 333 444");
        Room room = new Room("Test Room", Money.of("80"), 12, Set.of("projector", "whiteboard"));
        userRepository.add(user);
        resourceRepository.add(room);
        Booking testBooking = bookingService.book(user, room, LocalDateTime.of(2026, 8, 10, 10, 0),
                LocalDateTime.of(2026, 8, 10, 12, 0));
        System.out.println("\nTest booking: \n" + testBooking);

        System.out.println("\n* Payment Test *");
        Payment testPayment = paymentService.pay(testBooking.getId(), "1234");
        System.out.println(testPayment);

        System.out.println("\n* Booking Payment Test *");
        System.out.println(testBooking.getPayment());

        System.out.println("\n* Payment Status Test *");
        System.out.println("via payment: " + testPayment.getStatus());
        System.out.println("via booking: " + testBooking.getPayment().getStatus());
    }
}