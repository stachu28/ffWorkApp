package main.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.time.TimeUtils;

import java.time.LocalDateTime;

public class Booking {
    private static int bookingCounter = 1;
    private final String id;
    private final User user;
    private final Resource resource;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private BookingStatus status;
    private Money calculatedPrice;

    public Booking(User user, Resource resource, LocalDateTime start, LocalDateTime end) {
        new TimeUtils.TimeRange(start, end);
        this.id = generateId(start);
        this.user = user;
        this.resource = resource;
        this.start = start;
        this.end = end;
        this.status = BookingStatus.PENDING;
    }

    private static String generateId(LocalDateTime start) {
        return String.format("BK-%tY%<tm%<td-%d", start, bookingCounter++);
    }

    public int durationMinutes() {
        return (int) TimeUtils.durationMinutes(start, end);
    }


    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Resource getResource() {
        return resource;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void confirm() {
        if (status != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING booking can be confirmed.");
        }
        status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (status != BookingStatus.PENDING && status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Booking cannot be cancelled from status: " + status);
        }
        status = BookingStatus.CANCELLED;
    }

    public void complete() {
        if (status != BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED booking can be completed.");
        }
        status = BookingStatus.COMPLETED;
    }

    public Money getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(Money calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }
}