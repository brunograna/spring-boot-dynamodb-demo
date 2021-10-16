package com.demo.dynamodb.service;

import com.demo.dynamodb.controller.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface DatabaseService<T> {

    String save(T entity);
    void update(String id, T updatedEntity) throws NotFoundException;
    void delete(String id);
    Optional<T> findById(String id);
    List<T> findAll();
}
