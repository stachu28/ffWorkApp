package main.java.pl.ffWorkApp.service;

import main.java.pl.ffWorkApp.billing.Billable;
import main.java.pl.ffWorkApp.billing.Invoice;
import main.java.pl.ffWorkApp.domain.Booking;

import java.time.LocalDateTime;

public class BillingService implements Billable {
    int invoiceCounter;

    @Override
    public Invoice toInvoice(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        if (booking.getPayment() == null) {
            throw new IllegalArgumentException("Booking has not been paid");
        }
        String invoiceNumber = String.format("INV-%tY%<tm%<td-%d", LocalDateTime.now(), invoiceCounter++);
        String description = "Booking " + booking.getResource().getName() + " " + booking.getStart() + " - "
                + booking.getEnd();

        return new Invoice(invoiceNumber, LocalDateTime.now(), booking.getUser(), booking.getCalculatedPrice(),
                description);
    }
}