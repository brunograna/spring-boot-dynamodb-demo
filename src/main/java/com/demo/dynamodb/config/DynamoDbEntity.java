package com.demo.dynamodb.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;

public interface DynamoDbEntity {

    @JsonIgnore
    String getTableName();
    @JsonIgnore
    CreateTableRequest getTableRequest();
}
