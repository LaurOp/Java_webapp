package com.example.unisync.Service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    List<T> getAll();

    Optional<T> getById(Long id);

    void delete(Long id);

}