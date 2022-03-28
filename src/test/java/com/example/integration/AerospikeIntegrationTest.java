package com.example.integration;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.example.aerospike.configuration.AerospikeConfiguration;
import com.example.aerospike.configuration.AerospikeConfigurationProperties;
import io.micronaut.context.ApplicationContext;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AerospikeIntegrationTest {

    AerospikeConfiguration aerospikeConfiguration;

    AerospikeConfigurationProperties aerospikeConfigurationProperties;
    @Inject
    private static ApplicationContext context;

    @BeforeAll
    static void setup() {
        context = ApplicationContext.run();
    }
    AerospikeIntegrationTest() {
        this.aerospikeConfiguration = context.getBean(AerospikeConfiguration.class);
        this.aerospikeConfigurationProperties = context.getBean(AerospikeConfigurationProperties.class);
    }

    @Test
    void AeroSpikeClientShouldBeConnectedWithGivenProperties() {
        AerospikeClient aerospikeClient = aerospikeConfiguration.aerospikeClient(aerospikeConfigurationProperties);

        assertEquals(aerospikeClient.isConnected(), true);
    }

    @Test
    void AeroSpikeShouldAbleToFetchRecordWithKeyWhenRecordInserted() {
        AerospikeClient aerospikeClient = aerospikeConfiguration.aerospikeClient(aerospikeConfigurationProperties);
        Key key = new Key("test", "demo", "testkey");
        Bin bin1 = new Bin("bin1", "value1");
        Bin bin2 = new Bin("bin2", "value2");

        aerospikeClient.put(null, key, bin1, bin2);

        Record record = aerospikeClient.get(null, key);
        assertEquals(record.bins.get("bin1"), bin1.value.toString());
        assertEquals(record.bins.get("bin2"), bin2.value.toString());
    }
}
