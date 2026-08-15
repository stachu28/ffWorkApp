package main.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.money.Money;

public class Desk extends Resource {
    private final DeskType type;

    public Desk(String name, DeskType type) {
        super(name);
        this.type = type;
    }

    public Desk(String name, DeskType type, Money customHourlyRate) {
        super(name, customHourlyRate);
        this.type = type;
    }

    @Override
    protected Money baseRatePerHour() {
        return type == DeskType.HOT ? Money.of("20") : Money.of("30");
    }

    @Override
    public String describe() {
        return "Desk: " + getName() + ", type: " + type;
    }
}