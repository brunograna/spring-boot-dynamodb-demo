package com.demo.dynamodb.service;

import com.demo.dynamodb.AbstractDynamoDbContainerTest;
import com.demo.dynamodb.domain.Food;
import com.demo.dynamodb.mock.FoodMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDbDatabaseServiceTest extends AbstractDynamoDbContainerTest {

    @Autowired
    private DynamoDbDatabaseService dynamoDbDatabaseService;

    @Autowired
    private DynamoDbTable<Food> foodDynamoDbTable;

    @Test
    void findAll() {
        this.foodDynamoDbTable.putItem(FoodMock.any());
        var result = this.dynamoDbDatabaseService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

}