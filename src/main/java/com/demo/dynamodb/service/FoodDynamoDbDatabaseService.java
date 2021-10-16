package com.demo.dynamodb.service;

import com.demo.dynamodb.controller.NotFoundException;
import com.demo.dynamodb.domain.Food;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodDynamoDbDatabaseService implements DatabaseService<Food> {

    private final DynamoDbTable<Food> foodTable;

    public FoodDynamoDbDatabaseService(final DynamoDbEnhancedClient ddb) {
        this.foodTable = ddb.table(Food.config().getTableName(), TableSchema.fromBean(Food.class));
    }

    @Override
    public String save(Food food) {
        var creationFood = food.forCreation();
        foodTable.putItem(creationFood);
        return creationFood.getId();
    }

    @Override
    public void update(String id, Food updatedFood) throws NotFoundException {
        var item = Optional.ofNullable(
                                this.foodTable.getItem(
                                        Key.builder()
                                            .partitionValue(id)
                                            .build()
                                )
                        ).orElseThrow(() -> new NotFoundException("Food " + id + " not found"));

        this.foodTable.updateItem(item.update(updatedFood));
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
