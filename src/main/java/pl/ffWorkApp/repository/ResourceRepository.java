package main.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    void add(Resource r);

    Optional<Resource> findByName(String name);

    List<Resource> findAll();

    List<Resource> findByType(Class<? extends Resource> type);
}