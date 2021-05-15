package com.demo.dynamodb.service;

import com.demo.dynamodb.controller.NotFoundException;
import com.demo.dynamodb.domain.Food;

import java.util.List;
import java.util.Optional;

public interface DatabaseService {

    String save(Food food);
    void update(String id, Food updatedFood) throws NotFoundException;
    void delete(String id);
    Optional<Food> findById(String id);
    List<Food> findAll();
}
