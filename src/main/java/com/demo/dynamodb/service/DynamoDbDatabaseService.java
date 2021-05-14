package com.demo.dynamodb.service;

import com.demo.dynamodb.domain.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.Optional;

@Service
public class DynamoDbDatabaseService implements DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDbDatabaseService.class);

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Food> foodTable;

    public DynamoDbDatabaseService(final DynamoDbEnhancedClient dynamoDbEnhancedClient,
                                   final DynamoDbTable<Food> foodTable) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.foodTable = foodTable;
    }

    @Override
    public String save(Food food) {
        food.generateId();
        foodTable.putItem(food);

        logger.info("food created -> {}", food);

        return food.getId();
    }

    @Override
    public void update(String id, Food updatedFood) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Optional<Food> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Food> findAll() {
        return null;
    }
}