package main.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void add(User u) {
        if (findByEmail(u.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        users.add(u);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}