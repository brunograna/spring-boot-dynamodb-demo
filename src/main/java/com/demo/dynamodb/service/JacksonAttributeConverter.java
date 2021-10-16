package com.demo.dynamodb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.io.UncheckedIOException;

public class JacksonAttributeConverter<T> implements AttributeConverter<T> {

    private final TypeReference<T> clazz;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public JacksonAttributeConverter(TypeReference<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public AttributeValue transformFrom(T input) {
        try {
            return AttributeValue
                    .builder()
                    .s(mapper.writeValueAsString(input))
                    .build();
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Unable to serialize object", e);
        }
    }

    @Override
    public T transformTo(AttributeValue input) {
        try {
            return mapper.readValue(input.s(), this.clazz);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException("Unable to parse object", e);
        }
    }

    @Override
    public EnhancedType type() {
        return EnhancedType.of(this.clazz.getType());
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}