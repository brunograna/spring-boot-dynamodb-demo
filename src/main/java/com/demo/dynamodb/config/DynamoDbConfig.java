package com.demo.dynamodb.config;

import com.demo.dynamodb.domain.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    private final Logger logger = LoggerFactory.getLogger(DynamoDbConfig.class);
    private static final String FOODS_TABLE = "dynamodb-foods";

    @Bean
    public DynamoDbTable<Food> foodTable(DynamoDbEnhancedClient ddb) {
        return ddb.table(FOODS_TABLE, TableSchema.fromBean(Food.class));
    }

    public void createFoodTable(DynamoDbClient ddb) {
        DynamoDbWaiter dbWaiter = ddb.waiter();

        var tableRequest = DescribeTableRequest.builder()
                .tableName(FOODS_TABLE)
                .build();

        // Wait until the Amazon DynamoDB table is created
        var waiterResponse =  dbWaiter.waitUntilTableExists(tableRequest);
        waiterResponse.matched().response()
                .ifPresentOrElse((r) -> {
                    logger.info("Table already exists: {}", r.table().tableName());
                }, () -> {
                    var request = CreateTableRequest.builder()
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
                                    .readCapacityUnits(new Long(10))
                                    .writeCapacityUnits(new Long(10))
                                    .build())
                            .tableName(FOODS_TABLE)
                            .build();

                    var r = ddb.createTable(request);

                    logger.info("Table created: {}", r.tableDescription().tableName());
                });
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create("http://localhost:8000"))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient ddb) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }
}
