package test.java.pl.ffWorkApp.cli;

import main.java.pl.ffWorkApp.cli.Cli;
import main.java.pl.ffWorkApp.cli.CliFormatter;
import main.java.pl.ffWorkApp.cli.CommandParser;
import main.java.pl.ffWorkApp.pricing.StandardPricing;
import main.java.pl.ffWorkApp.repository.BookingRepository;
import main.java.pl.ffWorkApp.repository.ResourceRepository;
import main.java.pl.ffWorkApp.repository.UserRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryBookingRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryResourceRepository;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryUserRepository;
import main.java.pl.ffWorkApp.service.BillingService;
import main.java.pl.ffWorkApp.service.BookingService;
import main.java.pl.ffWorkApp.service.PaymentService;

import java.util.Scanner;

public class CliTest {

    public static void main(String[] args) {

        UserRepository userRepository =
                new InMemoryUserRepository();

        ResourceRepository resourceRepository =
                new InMemoryResourceRepository();

        BookingRepository bookingRepository =
                new InMemoryBookingRepository();

        BookingService bookingService =
                new BookingService(
                        userRepository,
                        resourceRepository,
                        bookingRepository,
                        new StandardPricing()
                );

        PaymentService paymentService =
                new PaymentService(bookingRepository);

        BillingService billingService =
                new BillingService();

        CommandParser parser =
                new CommandParser(new Scanner(System.in));

        Cli cli = new Cli(
                parser,
                new CliFormatter(),
                userRepository,
                resourceRepository,
                bookingRepository,
                bookingService,
                paymentService,
                billingService
        );

        cli.start();
    }
}