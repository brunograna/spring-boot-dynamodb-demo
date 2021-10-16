package com.demo.dynamodb.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Car extends Vehicle {

    private final String color;

    @JsonCreator
    public Car(@JsonProperty("id") String id, @JsonProperty("color") String color) {
        super(id, VehicleType.CAR);
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
