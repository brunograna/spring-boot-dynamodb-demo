package com.demo.dynamodb;

import com.demo.dynamodb.config.DynamoDbConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@SpringBootApplication
public class DynamodbApplication implements CommandLineRunner {

	private final DynamoDbConfig dynamoDbConfig;
	private final DynamoDbClient ddb;

	public DynamodbApplication(final DynamoDbConfig dynamoDbConfig,
							   final DynamoDbClient ddb) {
		this.dynamoDbConfig = dynamoDbConfig;
		this.ddb = ddb;
	}

	public static void main(String[] args) {
		SpringApplication.run(DynamodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.dynamoDbConfig.createFoodTable(ddb);
	}
}
