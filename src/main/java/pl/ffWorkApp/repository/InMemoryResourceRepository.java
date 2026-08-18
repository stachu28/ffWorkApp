package main.java.pl.ffWorkApp.repository;

import main.java.pl.ffWorkApp.domain.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryResourceRepository implements ResourceRepository {
    private final List<Resource> resources = new ArrayList<>();

    @Override
    public void add(Resource r) {
        if (findByName(r.getName()).isPresent()) {
            throw new IllegalArgumentException("Resource with this name already exists");
        }
        resources.add(r);
    }

    @Override
    public Optional<Resource> findByName(String name) {
        for (Resource resource : resources) {
            if (resource.getName().equalsIgnoreCase(name)) {
                return Optional.of(resource);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Resource> findAll() {
        return new ArrayList<>(resources);
    }

    @Override
    public List<Resource> findByType(Class<? extends Resource> type) {
        List<Resource> result = new ArrayList<>();
        for (Resource resource : resources) {
            if (type.isInstance(resource)) {
                result.add(resource);
            }
        }
        return result;
    }
}