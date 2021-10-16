package com.demo.dynamodb.service;

import com.demo.dynamodb.domain.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class VehicleConverter extends JacksonAttributeConverter<List<Vehicle>> {

    public VehicleConverter() {
        super(new TypeReference<List<Vehicle>>(){});
    }

}
