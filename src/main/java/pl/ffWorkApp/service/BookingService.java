package main.java.pl.ffWorkApp.service;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.domain.BookingStatus;
import main.java.pl.ffWorkApp.domain.Resource;
import main.java.pl.ffWorkApp.domain.User;
import main.java.pl.ffWorkApp.pricing.PricingPolicy;
import main.java.pl.ffWorkApp.repository.BookingRepository;
import main.java.pl.ffWorkApp.repository.ResourceRepository;
import main.java.pl.ffWorkApp.repository.UserRepository;
import main.java.pl.ffWorkApp.time.TimeUtils;

import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;
    private final PricingPolicy pricingPolicy;

    public BookingService(UserRepository userRepository, ResourceRepository resourceRepository, BookingRepository bookingRepository, PricingPolicy pricingPolicy) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.bookingRepository = bookingRepository;
        this.pricingPolicy = pricingPolicy;
    }

    public Booking book(User user, Resource resource, LocalDateTime start, LocalDateTime end) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (resource == null) {
            throw new IllegalArgumentException("Resource cannot be null");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start time and/or end time cannot be null");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        if (!isResourceAvailable(resource, start, end)) {
            throw new IllegalArgumentException("Resource is not available in selected timeframe");
        }
        Booking booking = new Booking(user, resource, start, end);

        booking.setCalculatedPrice(pricingPolicy.price(booking));

        bookingRepository.add(booking);

        return booking;
    }

    public Booking book(User user, Resource resource, LocalDateTime start, int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0 minutes.");
        }
        LocalDateTime end = start.plusMinutes(durationMinutes);
        return book(user, resource, start, end);
    }

    private boolean isResourceAvailable(Resource resource, LocalDateTime start, LocalDateTime end) {
        List<Booking> bookings = bookingRepository.findByResource(resource);
        TimeUtils.TimeRange requestedBookingRange = new TimeUtils.TimeRange(start, end);

        for (Booking booking : bookings) {
            if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.CONFIRMED) {
                continue;
            }
            TimeUtils.TimeRange existingBookingRange = new TimeUtils.TimeRange(booking.getStart(), booking.getEnd());
            if (TimeUtils.overlaps(requestedBookingRange, existingBookingRange)) {
                return false;
            }
        }
        return true;
    }

    public void confirm(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        booking.confirm();
    }

    public void cancel(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        booking.cancel();
    }

    public void complete(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        booking.complete();
    }

}