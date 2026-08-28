package main.java.pl.ffWorkApp.pricing;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.time.TimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StandardPricing implements PricingPolicy {

    @Override
    public Money price(Booking booking) {
        BigDecimal hours = BigDecimal.valueOf(TimeUtils.durationMinutes(booking.getStart(), booking.getEnd()))
                .divide(BigDecimal.valueOf(60));
        return booking.getResource().hourlyRate().multiply(hours);
    }
}