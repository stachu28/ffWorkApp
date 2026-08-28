package main.java.pl.ffWorkApp.billing;

import main.java.pl.ffWorkApp.domain.User;
import main.java.pl.ffWorkApp.money.Money;

import java.time.LocalDateTime;

public class Invoice {
    private final String invoiceNumber;
    private final LocalDateTime issueDate;
    private final User buyer;
    private final Money total;
    private final String invoiceDescription;

    public Invoice(
            String invoiceNumber,
            LocalDateTime issueDate,
            User buyer,
            Money total,
            String itemDescription) {
        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            throw new IllegalArgumentException("Invoice number cannot be empty");
        }
        if (issueDate == null) {
            throw new IllegalArgumentException("Issue date cannot be null");
        }
        if (buyer == null) {
            throw new IllegalArgumentException("Buyer cannot be null");
        }
        if (total == null) {
            throw new IllegalArgumentException("Invoice total amount cannot be null!");
        }
        if (itemDescription == null || itemDescription.isBlank()) {
            throw new IllegalArgumentException("Item description cannot be empty");
        }
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.buyer = buyer;
        this.total = total;
        this.invoiceDescription = itemDescription;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getTotal() {
        return total;
    }

    public String getInvoiceDescription() {
        return invoiceDescription;
    }

    @Override
    public String toString() {
        return "Invoice number: " + invoiceNumber + ", issue date: " + issueDate + ", buyer: " + buyer
                + ", total: " + total + ", item description: " + invoiceDescription;
    }
}