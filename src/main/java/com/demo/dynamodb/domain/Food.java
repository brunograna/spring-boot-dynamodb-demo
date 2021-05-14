package com.demo.dynamodb.domain;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.UUID;

@DynamoDbBean
public class Food {

    private String id;
    private String name;
    private Double weight;

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

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                '}';
    }

    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}
