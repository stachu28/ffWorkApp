package main.java.pl.ffWorkApp.cli;

import main.java.pl.ffWorkApp.billing.Invoice;
import main.java.pl.ffWorkApp.domain.*;
import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.payment.Payment;
import main.java.pl.ffWorkApp.pricing.HappyHoursPricing;
import main.java.pl.ffWorkApp.pricing.StandardPricing;
import main.java.pl.ffWorkApp.repository.BookingRepository;
import main.java.pl.ffWorkApp.repository.ResourceRepository;
import main.java.pl.ffWorkApp.repository.UserRepository;
import main.java.pl.ffWorkApp.service.BillingService;
import main.java.pl.ffWorkApp.service.BookingService;
import main.java.pl.ffWorkApp.service.PaymentService;

import java.time.LocalDateTime;
import java.util.Set;

public class Cli {
    private final CommandParser parser;
    private final CliFormatter formatter;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    private final BillingService billingService;

    public Cli(CommandParser parser, CliFormatter formatter, UserRepository userRepository,
               ResourceRepository resourceRepository, BookingRepository bookingRepository,
               BookingService bookingService, PaymentService paymentService,
               BillingService billingService) {
        this.parser = parser;
        this.formatter = formatter;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.paymentService = paymentService;
        this.billingService = billingService;
    }

    public void start() {
        boolean running = true;
        printOptions();

        while (running) {
            System.out.print("> ");

            String command = parser.readCommand();

            if (command.equalsIgnoreCase("QUIT")) {
                running = false;
            } else {
                execute(command);
            }
        }
        parser.close();
    }

    private void printOptions() {
        System.out.println("""
                
                === ffWork ===
                Available commands:
                ADD_USER INDIVIDUAL <email> <fullName> <phone> [studentId]
                ADD_USER COMPANY <email> <companyName> <nip>
                LIST_USERS
                
                ADD_ROOM <name> <seats> <hourlyRate>
                ADD_DESK <name> <hot|fixed> <hourlyRate>
                ADD_DEVICE <name> <quantity> <hourlyRate>
                LIST_RESOURCES
                
                BOOK <userEmail> <resourceName> <startIso> <endIso>
                BOOK <userEmail> <resourceName> <startIso> <durationMinutes>
                CONFIRM <bookingId>
                CANCEL <bookingId>
                LIST_BOOKINGS
                
                SET_PRICING STANDARD|HAPPY_HOURS
                
                PAY <bookingId> CARD <last4>
                INVOICE <bookingId>
                
                HELP
                QUIT
                """);
    }

    private void execute(String command) {
        String[] parts = parser.parse(command);

        switch (parts[0].toUpperCase()) {
            case "ADD_USER" -> addUser(parts);
            case "LIST_USERS" -> listUsers();
            case "ADD_ROOM" -> addRoom(parts);
            case "ADD_DESK" -> addDesk(parts);
            case "ADD_DEVICE" -> addDevice(parts);
            case "LIST_RESOURCES" -> listResources();
            case "BOOK" -> book(parts);
            case "CONFIRM" -> confirm(parts);
            case "CANCEL" -> cancel(parts);
            case "LIST_BOOKINGS" -> listBookings();
            case "SET_PRICING" -> setPricing(parts);
            case "PAY" -> pay(parts);
            case "INVOICE" -> invoice(parts);
            case "HELP" -> help();
            default -> formatter.error("Unknown command.");
        }
    }

    private void help() {
        System.out.println("""
                
                === ffWork CLI HELP ===
                
                In order to run a program, type one of the following commands into terminal:
                
                USERS:
                ADD_USER INDIVIDUAL <email> <fullName> <phone> [studentId]
                ADD_USER COMPANY <email> <companyName> <nip>
                LIST_USERS
                
                RESOURCES:
                ADD_ROOM <name> <seats> <hourlyRate>
                ADD_DESK <name> <hot|fixed> <hourlyRate>
                ADD_DEVICE <name> <quantity> <hourlyRate>
                LIST_RESOURCES
                
                BOOKINGS:
                BOOK <userEmail> <resourceName> <startIso> <endIso>
                BOOK <userEmail> <resourceName> <startIso> <durationMinutes>
                CONFIRM <bookingId>
                CANCEL <bookingId>
                LIST_BOOKINGS
                
                PRICING:
                SET_PRICING STANDARD|HAPPY_HOURS
                
                PAYMENTS:
                PAY <bookingId> CARD <last4>
                INVOICE <bookingId>
                
                HELP
                QUIT
                """);
    }

    private void invoice(String[] parts) {
        if (parts.length != 2) {
            formatter.error("Usage: INVOICE <bookingId>");
            return;
        }
        try {
            String bookingId = parts[1];
            Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                    new IllegalArgumentException("Booking not found!"));
            Invoice invoice = billingService.toInvoice(booking);
            formatter.ok("You generated invoice: " + invoice.getInvoiceNumber());
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void pay(String[] parts) {
        if (parts.length != 4) {
            formatter.error("Usage: PAY <bookingId> CARD <last4>");
            return;
        }
        try {
            String bookingId = parts[1];
            String paymentType = parts[2];
            String last4 = parts[3];
            if (!paymentType.equalsIgnoreCase("CARD")) {
                formatter.error("Only Card payments are supported!");
                return;
            }
            Payment payment = paymentService.pay(bookingId, last4);
            formatter.ok("Payment successful: " + payment.getPaymentId());
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void setPricing(String[] parts) {
        if (parts.length != 2) {
            formatter.error("Usage: SET_PRICING STANDARD|HAPPY_HOURS");
            return;
        }
        try {
            if (parts[1].equalsIgnoreCase("STANDARD")) {
                bookingService.setPricingPolicy(new StandardPricing());
                formatter.ok("Pricing policy changed to standard");
            } else if (parts[1].equalsIgnoreCase("HAPPY_HOURS")) {
                bookingService.setPricingPolicy(new HappyHoursPricing());
                formatter.ok("Pricing policy changed to happy hours");
            } else {
                formatter.error("Unknown pricing policy");
            }
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void listBookings() {
        try {
            for (Booking booking : bookingRepository.findAll()) {
                System.out.println(booking);
            }
            formatter.ok("Bookings listed: ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void cancel(String[] parts) {
        if (parts.length != 2) {
            formatter.error("Usage: CANCEL <bookingId>");
            return;
        }
        try {
            String bookingId = parts[1];
            bookingService.cancel(bookingId);
            formatter.ok("You confirmed booking: " + bookingId);
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void confirm(String[] parts) {
        if (parts.length != 2) {
            formatter.error("Usage: CONFIRM <bookingId>");
            return;
        }
        try {
            String bookingId = parts[1];
            bookingService.confirm(bookingId);
            formatter.ok("You confirmed booking: " + bookingId);
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void book(String[] parts) {
        try {
            String email = parts[1];
            String resourceName = parts[2].replace("\"", "");
            LocalDateTime start = parser.parseDateTime(parts[3]);
            User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new IllegalArgumentException("User not found!"));
            Resource resource = resourceRepository.findByName(resourceName).orElseThrow(() ->
                    new IllegalArgumentException("Resource not found!"));
            Booking booking;
            try {
                int durationMinutes = Integer.parseInt(parts[4]);
                booking = bookingService.book(user, resource, start, durationMinutes);
            } catch (NumberFormatException e) {
                LocalDateTime end = parser.parseDateTime(parts[4]);
                booking = bookingService.book(user, resource, start, end);
            }
            formatter.ok("You added booking: " + booking.getId() + ", status: " + booking.getStatus()
                    + ", price: " + booking.getCalculatedPrice());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listResources() {
        try {
            for (Resource resource : resourceRepository.findAll()) {
                System.out.println(resource.describe());
            }
            formatter.ok("Resources listed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addDevice(String[] parts) {
        if (parts.length != 4) {
            formatter.error("Usage: ADD_DEVICE <name> <quantity> <hourlyRate>");
            return;
        }
        try {
            String name = parts[1].replace("\"", "");
            int quantity = Integer.parseInt(parts[2]);
            Money customHourlyRate = Money.of(parts[3]);
            if (parts.length == 3) {
                Device device = new Device(name, quantity, customHourlyRate);
                resourceRepository.add(device);
                formatter.ok("You added: " + device.describe());
            }
            if (parts.length == 4) {
                Device device = new Device(name, quantity, customHourlyRate);
                resourceRepository.add(device);
                formatter.ok("You added: " + device.describe());
            }
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void addDesk(String[] parts) {
        if (parts.length != 4) {
            formatter.error("Usage: ADD_DESK <name> <hot|fixed> <hourlyRate>");
            return;
        }
        try {
            String name = parts[1].replace("\"", "");
            DeskType type = DeskType.valueOf(parts[2].toUpperCase());
            Money customHourlyRate = Money.of(parts[3]);
            if (parts.length == 3) {
                Desk desk = new Desk(name, type);
                resourceRepository.add(desk);
                formatter.ok("You added: " + desk.describe());
            }
            if (parts.length == 4) {
                Desk desk = new Desk(name, type, customHourlyRate);
                resourceRepository.add(desk);
                formatter.ok("You added: " + desk.describe());
            }
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }

    private void addRoom(String[] parts) {
        if (parts.length < 4) {
            formatter.error("Usage: ADD_ROOM <name> <seats> <hourlyRate>");
            return;
        }
        try {
            String name = parts[1].replace("\"", "");
            int seats = Integer.parseInt(parts[2]);
            Money hourlyRate = Money.of(parts[3]);
            Set<String> equipment = Set.of();
            if (parts.length >= 5) {
                equipment = Set.of(parts[4].split(","));
            }
            Room room = new Room(name, hourlyRate, seats, equipment);
            resourceRepository.add(room);
            formatter.ok("You added room: " + room.describe());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listUsers() {
        try {
            for (User user : userRepository.findAll()) {
                System.out.println(user);
            }
            formatter.ok("Users listed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addUser(String[] parts) {
        if (parts.length < 2) {
            formatter.error("Usage: ADD_USER INDIVIDUAL|COMPANY ...");
            return;
        }
        try {
            if (parts[1].equalsIgnoreCase("INDIVIDUAL")) {
                if (parts.length != 4 && parts.length != 5) {
                    formatter.error("Usage: ADD_USER INDIVIDUAL <email> <fullName> [studentId]");
                    return;
                }

                String email = parts[2];
                String name = parts[3].replace("_", " ");

                User user;

                if (parts.length == 5) {
                    long studentId = Long.parseLong(parts[4]);
                    user = new IndividualUser(email, name, studentId);
                } else {
                    user = new IndividualUser(email, name);
                }
                userRepository.add(user);
                formatter.ok("You added new individual user: " + user);
            } else if (parts[1].equalsIgnoreCase("COMPANY")) {
                if (parts.length != 5) {
                    formatter.error("Usage: ADD_USER COMPANY <email> <companyName> <nip>");
                    return;
                }
                String email = parts[2];
                String companyName = parts[3].replace("\"", "");
                String taxId = parts[4];
                CompanyUser companyUser = new CompanyUser(email, null, companyName, taxId);
                userRepository.add(companyUser);
                formatter.ok("You added new company user: " + companyUser);
            } else {
                System.out.println("Unknown user type");
            }
        } catch (Exception e) {
            formatter.error(e.getMessage());
        }
    }
}