package main.java.pl.ffWorkApp.billing;

import main.java.pl.ffWorkApp.domain.Booking;

public interface Billable {
    Invoice toInvoice(Booking booking);
}