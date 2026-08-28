package test.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.Desk;
import main.java.pl.ffWorkApp.domain.DeskType;
import main.java.pl.ffWorkApp.domain.Resource;
import main.java.pl.ffWorkApp.domain.Room;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryResourceRepository;

import java.util.Set;

public class ResourceRepositoryTest {
    static void main(String[] args) {
        InMemoryResourceRepository testRepository = new InMemoryResourceRepository();
        Room testRoom = new Room("Room 123", 4, Set.of("projector", "printer"));
        Desk testDesk = new Desk("Test Desk", DeskType.HOT);
        testRepository.add(testRoom);
        testRepository.add(testDesk);

        System.out.println("\n* Find By Name Test *");
        System.out.println(testRepository.findByName("Room 123").orElse(testRoom));
        System.out.println(testRepository.findByName("Room 321"));

        System.out.println("\n* Find All Test *");
        for (Resource resource : testRepository.findAll()) {
            System.out.println(resource.describe());
        }

        System.out.println("\n* Duplicate Room Test *");
        try {
            testRepository.add(new Room("Room 123", 4, Set.of()));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}