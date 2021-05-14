package com.demo.dynamodb.controller;

import com.demo.dynamodb.domain.Food;
import com.demo.dynamodb.service.DatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamodb/v1/foods")
public class FoodController {

    private final DatabaseService databaseService;

    public FoodController(final DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping
    public List<Food> findAll() {
        return this.databaseService.findAll();
    }

    @GetMapping("id")
    public ResponseEntity<Food> findById(@PathVariable("id") String id) {
        return this.databaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody Food updatedFood) {
        this.databaseService.update(id, updatedFood);
    }

    @DeleteMapping("id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String id) {
        this.databaseService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Food food) {
        this.databaseService.save(food);
    }

}
