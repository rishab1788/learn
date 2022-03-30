package com.example.aerospike.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("aerospike")
public class AerospikeConfigurationProperties {
    private String hostname;
    private Integer port;
    private String namespace;

    public String getHostname() {
        return hostname;
    }

    public Integer getPort() {
        return port;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
