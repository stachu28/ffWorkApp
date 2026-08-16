package main.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.money.Money;

public class Device extends Resource {
    private int quantity;

    public Device(String name, int quantity) {
        super(name);
        this.quantity = quantity;
    }

    public Device(String name, Money customHourlyRate, int quantity) {
        super(name, customHourlyRate);
        this.quantity = quantity;
    }

    @Override
    protected Money baseRatePerHour() {
        return Money.of("40");
    }

    @Override
    public String describe() {
        return "Device: " + getName() + ", quantity: " + quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}