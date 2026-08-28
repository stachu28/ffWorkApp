package main.java.pl.ffWorkApp.service;

import main.java.pl.ffWorkApp.domain.Booking;
import main.java.pl.ffWorkApp.payment.CardPayment;
import main.java.pl.ffWorkApp.payment.Payment;
import main.java.pl.ffWorkApp.repository.BookingRepository;

public class PaymentService {
    private final BookingRepository bookingRepository;

    public PaymentService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Payment pay(String bookingId, String last4) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new IllegalArgumentException("Booking " + bookingId + " not found"));
        if (booking.getCalculatedPrice() == null) {
            throw new IllegalArgumentException("Booking " + bookingId + " price has not been calculated");
        }
        if (booking.getPayment() != null) {
            throw new IllegalArgumentException("The booking " + bookingId + " has already been paid");
        }
        CardPayment cardPayment = new CardPayment(booking.getCalculatedPrice(), bookingId, last4);
        cardPayment.capture();
        booking.setPayment(cardPayment);
        return cardPayment;
    }
}