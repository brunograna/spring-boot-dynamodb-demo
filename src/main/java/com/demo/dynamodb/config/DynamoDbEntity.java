package com.demo.dynamodb.config;

import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest;

public interface DynamoDbEntity {

    String getTableName();
    CreateTableRequest getTableRequest();
}
