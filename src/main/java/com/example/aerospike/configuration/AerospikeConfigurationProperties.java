package com.example.aerospike.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("aerospike")
public class AerospikeConfigurationProperties {
    private String hostname;
    private Integer port;
    private String namespace;
}
