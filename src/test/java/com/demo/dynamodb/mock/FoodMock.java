package com.demo.dynamodb.mock;

import com.demo.dynamodb.domain.Food;

public class FoodMock {

    public static Food any() {
        return new Food(null, "Hamburger", 350.).forCreation();
    }
}
