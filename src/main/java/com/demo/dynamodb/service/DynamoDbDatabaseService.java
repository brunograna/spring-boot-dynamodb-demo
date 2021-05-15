package com.demo.dynamodb.service;

import com.demo.dynamodb.domain.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamoDbDatabaseService implements DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDbDatabaseService.class);

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Food> foodTable;

    public DynamoDbDatabaseService(final DynamoDbEnhancedClient ddb) {
        this.dynamoDbEnhancedClient = ddb;
        this.foodTable = ddb.table(Food.config().getTableName(), TableSchema.fromBean(Food.class));
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
        this.foodTable.deleteItem(
                Key.builder()
                        .partitionValue(id)
                        .build()
        );
    }

    @Override
    public Optional<Food> findById(String id) {
        return Optional.ofNullable(
                this.foodTable.getItem(
                        Key.builder()
                                .partitionValue(id)
                                .build()
                )
        );
    }

    @Override
    public List<Food> findAll() {
        return this.foodTable.scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }
}
