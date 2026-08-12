package main.java.pl.ffWorkApp.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;
    private final String currency = "PLN";

    public Money(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        if (this.amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("The amount cannot be negative!");
        }
    }

    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public Money add(Money other) {
        return new Money(amount.add(other.amount));
    }

    public Money subtract(Money other) {
        if (other.amount.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Desired operation failed due to unsufficient funds!");
        }
        return new Money(amount.subtract(other.amount));
    }

    public Money multiply(BigDecimal m) {
        return new Money(amount.multiply(m));
    }

    public Money multiply(double m) {
        return new Money(amount.multiply(BigDecimal.valueOf(m)));
    }

    public Money applyDiscount(BigDecimal discountPercent) {
        if (discountPercent.compareTo(BigDecimal.ZERO) < 0 || discountPercent.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Discount must be between 0 - 100");
        }
        BigDecimal discount = discountPercent.divide(BigDecimal.valueOf(100)).multiply(amount);
        return new Money(amount.subtract(discount));
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    public int compareTo(Money other) {
        return amount.compareTo(other.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}