package com.example.aerospike.configuration;

import com.aerospike.client.AerospikeClient;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class AerospikeConfiguration {
    @Singleton
    public AerospikeClient aerospikeClient(AerospikeConfigurationProperties aerospikeConfigurationProperties) {
        return new AerospikeClient(aerospikeConfigurationProperties.getHostname(), aerospikeConfigurationProperties.getPort());
    }
}
