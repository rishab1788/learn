package com.example.integration;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.IndexType;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.example.aerospike.configuration.AerospikeConfiguration;
import com.example.aerospike.configuration.AerospikeConfigurationProperties;
import com.example.constant.AerospikeConstants;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AerospikeIntegrationTest {
    private final String BIN_1_INDEX = "bin1index";
    @Inject
    private AerospikeConfiguration aerospikeConfiguration;
    @Inject
    private AerospikeConfigurationProperties aerospikeConfigurationProperties;

    @BeforeAll
    static void setup() {
    }

    @AfterAll
    void cleanup() {
        aerospikeConfiguration.aerospikeClient(aerospikeConfigurationProperties).close();
    }

    @Test
    void AeroSpikeClientShouldBeConnectedWithGivenProperties() {
        AerospikeClient aerospikeClient = aerospikeConfiguration.aerospikeClient(aerospikeConfigurationProperties);

        assertEquals(aerospikeClient.isConnected(), true);
        aerospikeClient.close();
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
        aerospikeClient.delete(null, key);
    }

    @Test
    void AeroSpikeShouldAbleTOFetchRecordWithSecondarKey() {
        AerospikeClient aerospikeClient = aerospikeConfiguration.aerospikeClient(aerospikeConfigurationProperties);
        aerospikeClient.createIndex(null, AerospikeConstants.NAMESPACE, AerospikeConstants.DEMO_SET, BIN_1_INDEX, "bin1", IndexType.STRING);
        Key key = new Key(AerospikeConstants.NAMESPACE, AerospikeConstants.DEMO_SET, "testkey");
        Bin bin1 = new Bin("bin1", "value1");
        Bin bin2 = new Bin("bin2", "value2");
        aerospikeClient.put(null, key, bin1, bin2);

        Statement statement = new Statement();
        statement.setNamespace(AerospikeConstants.NAMESPACE);
        statement.setSetName(AerospikeConstants.DEMO_SET);
        statement.setFilter(Filter.equal("bin1", "value1"));
        RecordSet recordSet = aerospikeClient.query(null, statement);
        for (var record : recordSet) {
            assertThat(record.record).isNotEqualTo(null);
        }


        aerospikeClient.dropIndex(null, AerospikeConstants.NAMESPACE, AerospikeConstants.DEMO_SET, BIN_1_INDEX);
        aerospikeClient.delete(null, key);
    }
}
