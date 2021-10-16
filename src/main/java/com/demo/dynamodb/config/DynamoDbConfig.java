package com.demo.dynamodb.config;

import com.demo.dynamodb.domain.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import java.net.URI;
import java.util.List;

@Configuration
public class DynamoDbConfig implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DynamoDbConfig.class);

    private final URI dynamodbUri;
    private final List<DynamoDbEntity> entities;

    public DynamoDbConfig(@Value("${aws.dynamodb.uri}") final String dynamodbStringUri,
                          List<DynamoDbEntity> entities) {

        if (dynamodbStringUri == null || dynamodbStringUri.isBlank())
            throw new IllegalArgumentException(
                    "DynamoDb Uri is required, the java property aws.dynamodb.uri must be set");

        this.dynamodbUri = URI.create(dynamodbStringUri);
        this.entities = entities;
    }

    public void createTables(DynamoDbClient ddb) {
        this.entities.forEach(t -> {
            var tableRequest = DescribeTableRequest.builder()
                    .tableName(t.getTableName())
                    .build();

            try {
                var response = ddb.describeTable(tableRequest);
                logger.info("Table '{}' existence verified", response.table().tableName());

            } catch (ResourceNotFoundException e) {
                logger.warn("Table '{}' not exists, creating...", t.getTableName());

                ddb.createTable(t.getTableRequest());

                DynamoDbWaiter dbWaiter = ddb.waiter();

                var waiterResponse = dbWaiter.waitUntilTableExists(tableRequest);

                waiterResponse.matched()
                        .exception()
                        .ifPresent(exception -> {
                            logger.error("Table '{}' not created, details: {}",
                                    t.getTableName(), exception);
                            System.exit(-1);
                        });

                waiterResponse.matched()
                        .response()
                        .ifPresentOrElse((response ->
                                logger.info("Table '{}' created successfully", response.table().tableName())
                        ), () -> {
                            logger.error("Table '{}' not created, verify your connection with the database",
                                    t.getTableName());
                            System.exit(-1);
                        });
            }
        });
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(this.dynamodbUri)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient ddb) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }

    @Bean
    public DynamoDbTable<Food> foodDynamoDbTable(DynamoDbEnhancedClient ddb) {
        return ddb.table(Food.config().getTableName(), TableSchema.fromBean(Food.class));
    }

    @Override
    public void run(String... args) {
        this.createTables(dynamoDbClient());
    }
}
