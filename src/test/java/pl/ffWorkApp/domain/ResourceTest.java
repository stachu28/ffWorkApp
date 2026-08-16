package test.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.domain.Desk;
import main.java.pl.ffWorkApp.domain.DeskType;
import main.java.pl.ffWorkApp.domain.Device;
import main.java.pl.ffWorkApp.domain.Room;
import main.java.pl.ffWorkApp.money.Money;

import java.util.Set;

public class ResourceTest {
    static void main(String[] args) {
        System.out.println("* Room Creation Test *");
        Room room = new Room("Test Room", 10, Set.of("projector", "printer", "computer"));
        System.out.println("Name: " + room.getName());
        System.out.println("Description: " + room.describe());
        System.out.println("Hourly rate: " + room.hourlyRate());

        System.out.println("\n* Hot Desk Test *");
        Desk hotDesk = new Desk("Desk 1", DeskType.HOT);
        System.out.println("Name: " + hotDesk.getName());
        System.out.println("Description: " + hotDesk.describe());
        System.out.println("Hourly rate: " + hotDesk.hourlyRate());

        System.out.println("\n* Fixed Desk Test *");
        Desk fixedDesk = new Desk("Desk 2", DeskType.FIXED);
        System.out.println("Name: " + fixedDesk.getName());
        System.out.println("Description: " + fixedDesk.describe());
        System.out.println("Hourly rate: " + fixedDesk.hourlyRate());

        System.out.println("\n* Device Test *");
        Device device = new Device("Laptop", 5);
        System.out.println("Name: " + device.getName());
        System.out.println("Quantity: " + device.getQuantity());
        System.out.println("Description: " + device.describe());
        System.out.println("Hourly rate: " + device.hourlyRate());

        System.out.println("\n* Custom Hourly Rate Test *");
        Room room2 = new Room("Test Room 2", Money.of("100.00"), 20, Set.of("Projector", "Whiteboard"));
        System.out.println("Default room rate: " + Money.of("80.00"));
        System.out.println("Custom rate: " + room2.getCustomHourlyRate());
        System.out.println("Actual hourly rate: " + room2.hourlyRate());

    }
}