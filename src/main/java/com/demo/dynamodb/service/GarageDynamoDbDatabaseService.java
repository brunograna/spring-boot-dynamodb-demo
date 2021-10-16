package com.demo.dynamodb.service;

import com.demo.dynamodb.controller.NotFoundException;
import com.demo.dynamodb.domain.Garage;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarageDynamoDbDatabaseService implements DatabaseService<Garage> {

    private final DynamoDbTable<Garage> garageTable;

    public GarageDynamoDbDatabaseService(final DynamoDbEnhancedClient ddb) {
        this.garageTable = ddb.table(Garage.config().getTableName(), TableSchema.fromBean(Garage.class));
    }

    @Override
    public String save(Garage garage) {
        var creationGarage = garage.forCreation();
        garageTable.putItem(creationGarage);
        return creationGarage.getId();
    }

    @Override
    public void update(String id, Garage updatedGarage) throws NotFoundException {
        var item = Optional.ofNullable(
                                this.garageTable.getItem(
                                        Key.builder()
                                            .partitionValue(id)
                                            .build()
                                )
                        ).orElseThrow(() -> new NotFoundException("Garage " + id + " not found"));

        this.garageTable.updateItem(item.update(updatedGarage));
    }

    @Override
    public void delete(String id) {
        this.garageTable.deleteItem(
                Key.builder()
                        .partitionValue(id)
                        .build()
        );
    }

    @Override
    public Optional<Garage> findById(String id) {
        return Optional.ofNullable(
                this.garageTable.getItem(
                        Key.builder()
                                .partitionValue(id)
                                .build()
                )
        );
    }

    @Override
    public List<Garage> findAll() {
        return this.garageTable.scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }
}
