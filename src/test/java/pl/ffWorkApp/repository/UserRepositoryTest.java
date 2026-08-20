package test.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.IndividualUser;
import main.java.pl.ffWorkApp.domain.User;
import main.java.pl.ffWorkApp.repository.inmemory.InMemoryUserRepository;

public class UserRepositoryTest {
    static void main(String[] args) {
        InMemoryUserRepository testRepository = new InMemoryUserRepository();
        IndividualUser testUser = new IndividualUser("test@user.com", "John McAfee");
        testRepository.add(testUser);

        System.out.println("\n* Find By Email Test *");
        System.out.println(testRepository.findByEmail("test@user.com").orElse(testUser));
        System.out.println(testRepository.findByEmail("wrong@email.adress"));

        System.out.println("\n* Find All Test *");
        for (User user : testRepository.findAll()) {
            System.out.println(testUser);
        }

        System.out.println("\n* Duplicate Email Test *");
        try {
            testRepository.add(new IndividualUser("test@user.com", "Kate McAfee"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}