package com.demo.dynamodb.controller;

import com.demo.dynamodb.domain.Garage;
import com.demo.dynamodb.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamodb/v1/garages")
public class GarageController {

    private static final Logger logger = LoggerFactory.getLogger(GarageController.class);
    private final DatabaseService<Garage> databaseService;

    public GarageController(final DatabaseService<Garage> databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping
    public List<Garage> findAll() {
        logger.info("findAll - executed");
        return this.databaseService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Garage> findById(@PathVariable("id") String id) {
        logger.info("findById - executed");
        return this.databaseService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") String id, @RequestBody Garage updatedGarage) throws NotFoundException {
        logger.info("update - executed");
        this.databaseService.update(id, updatedGarage);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        logger.info("deleteById - executed");
        return this.databaseService.findById(id)
                .map(Garage -> {
                    this.databaseService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Garage garage) {
        logger.info("save - executed");
        this.databaseService.save(garage);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Garage> notFoundExceptionHandler(NotFoundException e) {
        logger.error("notFoundExceptionHandler - message: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

}
