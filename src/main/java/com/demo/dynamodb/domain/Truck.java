package com.demo.dynamodb.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Truck extends Vehicle {

    private final Integer containerCapacity;

    @JsonCreator
    public Truck(@JsonProperty("id") String id,
                 @JsonProperty("container_capacity") Integer containerCapacity) {
        super(id, VehicleType.TRUCK);
        this.containerCapacity = containerCapacity;
    }

    public Integer getContainerCapacity() {
        return containerCapacity;
    }
}
