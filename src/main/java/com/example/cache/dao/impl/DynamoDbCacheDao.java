package com.example.cache.dao.impl;

import com.example.cache.dao.CacheDao;
import com.example.cache.exception.KeyNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Profile("dynamodb")
public class DynamoDbCacheDao implements CacheDao {

    private static final String DEFAULT_VALUE_ATTRIBUTE = "cacheValue";
    private static final String DEFAULT_KEY_ATTRIBUTE = "cacheKey";

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;
    private final String keyAttribute;
    private final String valueAttribute;

    public DynamoDbCacheDao(
            DynamoDbClient dynamoDbClient,
            @Value("${cache.dynamodb.table-name}") String tableName,
            @Value("${cache.dynamodb.key-attribute:}" ) String keyAttribute,
            @Value("${cache.dynamodb.value-attribute:}" ) String valueAttribute) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = tableName;
        this.keyAttribute = keyAttribute == null || keyAttribute.isBlank() ? DEFAULT_KEY_ATTRIBUTE : keyAttribute;
        this.valueAttribute = valueAttribute == null || valueAttribute.isBlank() ? DEFAULT_VALUE_ATTRIBUTE : valueAttribute;
    }

    @Override
    public void put(String key, String value) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put(keyAttribute, AttributeValue.builder().s(key).build());
        item.put(valueAttribute, AttributeValue.builder().s(value).build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(putItemRequest);
    }

    @Override
    public String get(String key) throws KeyNotFoundException {
        Map<String, AttributeValue> keyMap = Map.of(
                keyAttribute, AttributeValue.builder().s(key).build()
        );

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(keyMap)
                .consistentRead(true)
                .build();

        GetItemResponse response = dynamoDbClient.getItem(getItemRequest);

        if (response.hasItem()) {
            return Optional.ofNullable(response.item().get(valueAttribute))
                    .map(AttributeValue::s)
                    .orElseThrow(() -> new KeyNotFoundException(key));
        }

        throw new KeyNotFoundException(key);
    }
}

