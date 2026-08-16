package test.java.pl.ffWorkApp.pricing;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.domain.IndividualUser;
import main.java.pl.ffWorkApp.domain.Resource;
import main.java.pl.ffWorkApp.domain.User;
import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.pricing.HappyHoursPricing;
import main.java.pl.ffWorkApp.pricing.PricingPolicy;
import main.java.pl.ffWorkApp.pricing.StandardPricing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PricingTest {
    static void main(String[] args) {
        Resource testResource = new Resource("Test Resource", Money.of("100")) {
            @Override
            protected Money baseRatePerHour() {
                return Money.of("100");
            }

            @Override
            public String describe() {
                return "Test Resource";
            }
        };

        User testUser = new IndividualUser("test@email.com", "Test User", "+48 223 334 556");

        System.out.println("* Standard Pricing Test *");
        Booking testBooking = new Booking(testUser, testResource, LocalDateTime.of(2026, 8, 16, 14, 0),
                LocalDateTime.of(2026, 8, 16, 16, 0));
        PricingPolicy standardPricing = new StandardPricing();
        System.out.println("Hourly rate: " + testResource.hourlyRate());
        System.out.println("Booking duration: " + testBooking.durationMinutes() + " minutes");
        System.out.println("Standard price: " + standardPricing.price(testBooking));

        System.out.println("\n* Happy Hours Pricing Test *");
        Booking happyHoursBooking = new Booking(testUser, testResource, LocalDateTime.of(2026, 8, 16, 14, 0),
                LocalDateTime.of(2026, 8, 16, 16, 0));
        HappyHoursPricing happyHoursPricing = new HappyHoursPricing();
        System.out.println("Hourly rate: " + testResource.hourlyRate());
        System.out.println("Booking duration: " + happyHoursBooking.durationMinutes() + " minutes");
        System.out.println("Happy hours price: " + happyHoursPricing.price(happyHoursBooking));

        System.out.println("\n* 30% Discount Test *");
        Money normalPrice = standardPricing.price(testBooking);
        Money discountedPrice = happyHoursPricing.price(testBooking);
        System.out.println("Normal price: " + normalPrice);
        System.out.println("Discounted price: " + discountedPrice);

        System.out.println("\n* Custom Discount Test *");
        BigDecimal discount = BigDecimal.valueOf(50);
        Money bookingPrice = standardPricing.price(testBooking).applyDiscount(discount);
        Money finalPrice = bookingPrice.applyDiscount(discount);
        System.out.println("Booking price: " + bookingPrice);
        System.out.println("Discount rate: " + discount + "%");
        System.out.println("Final price: " + finalPrice);
    }
}