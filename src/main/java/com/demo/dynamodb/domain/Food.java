package com.demo.dynamodb.domain;

import com.demo.dynamodb.config.DynamoDbEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.UUID;

@Component
@DynamoDbBean
public class Food implements DynamoDbEntity {

    private String id;
    private String name;
    private Double weight;

    public Food() {
    }

    public Food(String id, String name, Double weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Food forCreation() {
        return new Food(UUID.randomUUID().toString(), this.name, this.weight);
    }

    public Food update(Food updatedFood) {
        return new Food(this.id, updatedFood.getName(), updatedFood.getWeight());
    }

    public static DynamoDbEntity config() {
        return new Food();
    }

    @Override
    public String getTableName() {
        return "dynamodb-foods-tableS";
    }

    @Override
    public CreateTableRequest getTableRequest() {
        return CreateTableRequest.builder()
                .attributeDefinitions(
                        AttributeDefinition.builder()
                                .attributeName("id")
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName("id")
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L)
                        .writeCapacityUnits(10L)
                        .build())
                .tableName(this.getTableName())
                .build();
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }
}
