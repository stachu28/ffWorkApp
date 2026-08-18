package test.java.pl.ffWorkApp.billing;

import main.java.pl.ffWorkApp.billing.Invoice;
import main.java.pl.ffWorkApp.domain.CompanyUser;
import main.java.pl.ffWorkApp.money.Money;

import java.time.LocalDateTime;

public class BillingTest {
    static void main(String[] args) {
        System.out.println("* Invoice Creation Test *");
        CompanyUser buyer = new CompanyUser("user@company.com", "John Doe", "+48 600 700 800",
                "TestCompany Ltd.", "521621721");
        Invoice testInvoice = new Invoice("0001", LocalDateTime.now(), buyer,
                Money.of("100"), "Test invoice description");
        System.out.println("Invoice number: " + testInvoice.getInvoiceNumber());
        System.out.println("Issue date: " + testInvoice.getIssueDate());
        System.out.println("Buyer: " + testInvoice.getBuyer());
        System.out.println("Total: " + testInvoice.getTotal());
        System.out.println("Description: " + testInvoice.getInvoiceDescription());

        System.out.println("\n* Invoice Validation Test *");
        try {
            new Invoice("", LocalDateTime.now(), buyer, Money.of("100.00"), "Test invoice description");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Invoice(null, LocalDateTime.now(), buyer, Money.of("100.00"), "Test invoice description");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Invoice("0002", LocalDateTime.now(), null, Money.of("100.00"), "Test invoice description");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Invoice("0003", LocalDateTime.now(), buyer, null, "Test invoice description");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Invoice("0004", LocalDateTime.now(), buyer, Money.of("100.00"), "");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Invoice("0003", LocalDateTime.now(), buyer, Money.of("100.00"), null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Invoice ToString Test *");
        System.out.println(testInvoice);

    }
}