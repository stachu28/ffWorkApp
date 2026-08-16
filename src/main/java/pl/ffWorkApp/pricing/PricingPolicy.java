package main.java.pl.ffWorkApp.pricing;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.money.Money;

public interface PricingPolicy {
    Money price(Booking booking);
}