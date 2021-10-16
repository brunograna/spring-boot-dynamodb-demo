package com.demo.dynamodb.domain;

import com.demo.dynamodb.config.DynamoDbEntity;
import com.demo.dynamodb.service.VehicleConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.UUID;

@Component
@DynamoDbBean
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Garage implements DynamoDbEntity {

    private String id;

    private List<Vehicle> vehicles;

    @Deprecated
    public Garage() {
    }

    public Garage(String id, List<Vehicle> vehicles) {
        this.id = id;
        this.vehicles = vehicles;
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbConvertedBy(VehicleConverter.class)
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public static DynamoDbEntity config() {
        return new Garage();
    }

    public Garage forCreation() {
        return new Garage(UUID.randomUUID().toString(), this.vehicles);
    }

    public Garage update(Garage updatedGarage) {
        return new Garage(this.id, updatedGarage.getVehicles());
    }

    @Override
    @JsonIgnore
    public String getTableName() {
        return "garage-table";
    }

    @Override
    @JsonIgnore
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
}
