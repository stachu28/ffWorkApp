package test.java.pl.ffWorkApp.money;

import main.java.pl.ffWorkApp.money.Money;

import java.math.BigDecimal;

public class MoneyTest {
    static void main(String[] args) {
        System.out.println("* Money Creation Test *");
        Money money = Money.of("299.99");
        System.out.println("Created amount: " + money.toString());

        System.out.println("\n* Money Rounding Test *");
        Money amountToRound = Money.of("123.456");
        System.out.println("Rounded amount: " + amountToRound);

        System.out.println("\n* Negative Amount Test *");
        String negativeAmountTest = "-50";
        System.out.println("Tested Value Object equals: " + negativeAmountTest);
        try {
            Money.of(negativeAmountTest);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Adding Money Test *");
        Money amountToAdd = Money.of("100");
        Money amountAdded = Money.of("200");
        System.out.println("Amount to add: " + amountToAdd + "\nAmount added: " + amountAdded);
        System.out.println("Result: " + amountToAdd.add(amountAdded));

        System.out.println("\n* Subtracting Money Test *");
        Money amountToSubtract = Money.of("200");
        Money amountSubtracted = Money.of("100");
        System.out.println("Amount to subtract: " + amountToSubtract + "\nAmount subtracted: " + amountSubtracted);
        System.out.println("Result: " + amountToSubtract.subtract(amountSubtracted));

        System.out.println("\n* Subtracting More Than Amount Test *");
        Money amountToSubtract2 = Money.of("100");
        Money amountSubtracted2 = Money.of("200");
        try {
            System.out.println("Result: " + amountToSubtract2.subtract(amountSubtracted2));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Multiplying Money With Object Value Test *");
        Money amountToMultiply = Money.of("100");
        BigDecimal multiplier = new BigDecimal("100");
        System.out.println("Amount to multiply: " + amountToMultiply + "\nMultiplier: " + multiplier);
        System.out.println(amountToMultiply.multiply(multiplier));

        System.out.println("\n* Multiplying Money With Primitive Double Value Test *");
        double multiplierDouble = 3.0;
        System.out.println("Amount to multiply: " + amountToMultiply + "\nDouble Value: " + multiplierDouble);
        System.out.println("Result: " + amountToMultiply.multiply(multiplierDouble));

        System.out.println("\n* Applying Discount Test *");
        Money amountToDiscount = Money.of("1000");
        BigDecimal appliedDiscount = new BigDecimal("25");
        System.out.println("Amount to discount: " + amountToDiscount + "\nDiscount rate: " + appliedDiscount + "%");
        System.out.println("Result: " + amountToDiscount.applyDiscount(appliedDiscount));

        System.out.println("\n* Applying Illegal Discount rate *");
        BigDecimal illegalDiscountRate = new BigDecimal("200");
        System.out.println("Amount to discount: " + amountToDiscount + "\nDiscount rate: " + illegalDiscountRate + "%");
        try {
            System.out.println("Result: " + amountToDiscount.applyDiscount(illegalDiscountRate));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n* Comparing Money Objects Test *");
        Money money1 = Money.of("1000");
        Money money2 = Money.of("2000");
        Money money3 = Money.of("2000");
        System.out.println(money1 + " compareTo " + money2 + " = " + money1.compareTo(money2));
        System.out.println(money2 + " compareTo " + money3 + " = " + money2.compareTo(money3));
        System.out.println(money3 + " compareTo" + money1 + " = " + money3.compareTo(money1));
        System.out.println(money1 + " equals " + money2 + " = " + money1.equals(money2));
        System.out.println(money2 + " equals " + money3 + " = " + money2.equals(money3));
        System.out.println(money3 + " equals " + money1 + " = " + money3.equals(money1));
        System.out.println(money1 + " hashcode: " + money1.hashCode());
        System.out.println(money2 + " hashcode: " + money2.hashCode());
        System.out.println(money3 + " hashcode: " + money3.hashCode());
    }
}