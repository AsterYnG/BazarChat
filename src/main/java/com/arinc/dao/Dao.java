package com.arinc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K,T> {
    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    T update(T entity);

    void save(T entity);
}