package main.java.pl.ffWorkApp.repository.inmemory;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.domain.Resource;
import main.java.pl.ffWorkApp.domain.User;
import main.java.pl.ffWorkApp.repository.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBookingRepository implements BookingRepository {
    private final List<Booking> bookings = new ArrayList<>();

    @Override
    public void add(Booking b) {
        if (findById(b.getId()).isPresent()) {
            throw new IllegalArgumentException("Booking with this ID already exists");
        }
        bookings.add(b);
    }

    @Override
    public Optional<Booking> findById(String id) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(id)) {
                return Optional.of(booking);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    @Override
    public List<Booking> findByResource(Resource r) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getResource().equals(r)) {
                result.add(booking);
            }
        }
        return result;
    }

    @Override
    public List<Booking> findByUser(User u) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUser().equals(u)) {
                result.add(booking);
            }
        }
        return result;
    }
}