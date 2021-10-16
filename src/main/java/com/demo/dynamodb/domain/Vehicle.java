package com.demo.dynamodb.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "CAR"),
        @JsonSubTypes.Type(value = Truck.class, name = "TRUCK")
})
public abstract class Vehicle {

    protected String id;

    protected VehicleType type;

    @Deprecated
    public Vehicle() {
    }

    protected Vehicle(String id, VehicleType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }
}
