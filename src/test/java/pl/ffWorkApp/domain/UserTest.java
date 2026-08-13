package test.java.pl.ffWorkApp.domain;

import main.java.pl.ffWorkApp.domain.CompanyUser;
import main.java.pl.ffWorkApp.domain.IndividualUser;

public class UserTest {
    static void main(String[] args) {
        System.out.println("* Individual User Test *");
        IndividualUser notStudent = new IndividualUser("user1@test.com", "Individual User 1", "+48 111 222 333");
        IndividualUser student = new IndividualUser("user2@test.com", "Individual User 2", "+48 444 555 666", 123456);
        System.out.println("First test user is: " + notStudent);
        System.out.println("Sedond test user is: " + student);

        System.out.println("\n* Company User Test *");
        CompanyUser companyUser = new CompanyUser("companyuser1@test.com", "Company User 1", "+48 123 456 789",
                "Company Name", "123456");
        System.out.println(companyUser);
    }
}