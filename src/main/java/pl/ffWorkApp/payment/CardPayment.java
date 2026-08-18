package main.java.pl.ffWorkApp.payment;

import main.java.pl.ffWorkApp.money.Money;

public class CardPayment extends Payment {
    private final String last4;

    public CardPayment(Money amount, String paymentId, String last4) {
        super(amount, paymentId);
        if (last4 == null || !last4.matches("\\d{4}")) {
            throw new IllegalArgumentException("Card's 'last four digits' must contain exactly 4 digits");
        }
        this.last4 = last4;
    }

    public String getLast4() {
        return last4;
    }

    @Override
    public void capture() {
        if (getStatus() == PaymentStatus.CAPTURED) {
            throw new IllegalArgumentException("Payment is already captured!");
        }
        markAsCaptured();
    }

    @Override
    public String toString() {
        return "Payment ID : " + getPaymentId() + ", amount: " + getAmount() + ", status: " + getStatus()
                + " - card payment, last four digits: " + getLast4();
    }
}