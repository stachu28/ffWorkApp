package main.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void add(User u);

    Optional<User> findByEmail(String email);

    List<User> findAll();
}