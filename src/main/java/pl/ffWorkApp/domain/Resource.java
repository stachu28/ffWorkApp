package main.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.money.Money;

public abstract class Resource {
    private final String name;
    private final Money customHourlyRate;

    public Resource(String name) {
        this(name, null);
    }

    public Resource(String name, Money customHourlyRate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Resource object must be named!");
        }
        this.name = name;
        this.customHourlyRate = customHourlyRate;
    }

    public String getName() {
        return name;
    }

    protected abstract Money baseRatePerHour();

    public abstract String describe();

    public Money hourlyRate() {
        if (customHourlyRate != null) {
            return customHourlyRate;
        }
        return baseRatePerHour();
    }

    public Money getCustomHourlyRate() {
        return customHourlyRate;
    }
}