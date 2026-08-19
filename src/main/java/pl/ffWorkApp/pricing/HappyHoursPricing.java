package main.java.pl.ffWorkApp.pricing;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.time.TimeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class HappyHoursPricing implements PricingPolicy {
    BigDecimal discount = BigDecimal.valueOf(30);

    @Override
    public Money price(Booking booking) {
        BigDecimal hours = BigDecimal.valueOf(TimeUtils.durationMinutes(booking.getStart(), booking.getEnd()))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        Money price = booking.getResource().hourlyRate().multiply(hours);
        if (isHappyHours(booking)) {
            return price.applyDiscount(discount);
        }
        return price;
    }

    private boolean isHappyHours(Booking booking) {
        return !booking.getStart().toLocalTime().isBefore(LocalTime.of(14, 0))
                && !booking.getEnd().toLocalTime().isAfter(LocalTime.of(16, 0));
    }
}