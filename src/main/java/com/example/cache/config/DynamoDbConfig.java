package com.example.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.Optional;

@Configuration
@Profile("dynamodb")
public class DynamoDbConfig {

    @Bean
    public DynamoDbClient dynamoDbClient(
            @Value("${cache.dynamodb.region}") String region,
            @Value("${cache.dynamodb.endpoint:}") String endpoint,
            AwsCredentialsProvider credentialsProvider,
            SdkHttpClient sdkHttpClient) {

        DynamoDbClient.Builder builder = DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(credentialsProvider)
                .httpClient(sdkHttpClient);

        Optional.ofNullable(endpoint)
                .filter(e -> !e.isBlank())
                .map(URI::create)
                .ifPresent(builder::endpointOverride);

        return builder.build();
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return DefaultCredentialsProvider.create();
    }

    @Bean
    public SdkHttpClient sdkHttpClient() {
        return UrlConnectionHttpClient.create();
    }
}

