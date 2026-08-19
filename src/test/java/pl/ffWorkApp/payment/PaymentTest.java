package test.java.pl.ffWorkApp.payment;

import main.java.pl.ffWorkApp.money.Money;
import main.java.pl.ffWorkApp.payment.CardPayment;
import main.java.pl.ffWorkApp.payment.PaymentStatus;

public class PaymentTest {
    static void main(String[] args) {
        System.out.println("* Card Payment Creation Test *");
        Money amount = Money.of("100");
        CardPayment testPayment = new CardPayment(amount, "Tst-001", "4321");
        System.out.println("Payment ID: " + testPayment.getPaymentId());
        System.out.println("Amount: " + testPayment.getAmount());
        System.out.println("Card last 4 digits: " + testPayment.getLast4());
        System.out.println("Payment status: " + testPayment.getStatus());

        System.out.println("\n* Initial Status Test *");
        if (testPayment.getStatus() == PaymentStatus.INITIATED) {
            System.out.println("Status: " + testPayment.getStatus() + " - is correct!");
        } else {
            System.out.println("ERROR: Expected " + PaymentStatus.INITIATED + ", got: " + testPayment.getStatus());
        }

        System.out.println("\n* Capture Payment Test *");
        testPayment.capture();
        System.out.println("Payment status after capture: " + testPayment.getStatus());

        System.out.println("\n* Invalid Card Number Test *");
        try {
            new CardPayment(Money.of("100"), "Tst-002", "123");
            System.out.println("Successful Card Payment!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Payment ToString Test *");
        System.out.println(testPayment);
    }
}