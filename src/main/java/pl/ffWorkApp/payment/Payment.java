package main.java.pl.ffWorkApp.payment;

import main.java.pl.ffWorkApp.money.Money;

public abstract class Payment {
    private final Money amount;
    private final String paymentId;
    private PaymentStatus status;

    public Payment(Money amount, String paymentId) {
        if (amount == null) {
            throw new IllegalArgumentException("Payment cannot be an empty value!");
        }
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be an empty value!");
        }
        this.amount = amount;
        this.paymentId = paymentId;
        this.status = PaymentStatus.INITIATED;
    }

    public Money getAmount() {
        return amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    protected void markAsCaptured() {
        status = PaymentStatus.CAPTURED;
    }

    public abstract void capture();

    @Override
    public String toString() {
        return "Payment ID : " + paymentId + ", amount: " + amount + ", status: " + status;
    }
}