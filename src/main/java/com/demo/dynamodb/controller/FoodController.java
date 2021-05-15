package com.demo.dynamodb.controller;

import com.demo.dynamodb.domain.Food;
import com.demo.dynamodb.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamodb/v1/foods")
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);
    private final DatabaseService databaseService;

    public FoodController(final DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping
    public List<Food> findAll() {
        logger.info("findAll - executed");
        return this.databaseService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Food> findById(@PathVariable("id") String id) {
        logger.info("findById - executed");
        return this.databaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody Food updatedFood) throws NotFoundException {
        logger.info("update - executed");
        this.databaseService.update(id, updatedFood);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        logger.info("deleteById - executed");
        return this.databaseService.findById(id)
                .map(food -> {
                    this.databaseService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Food food) {
        logger.info("save - executed");
        this.databaseService.save(food);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Food> notFoundExceptionHandler(NotFoundException e) {
        logger.error("notFoundExceptionHandler - message: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

}
