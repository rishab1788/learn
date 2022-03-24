package com.example.controllers;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class HelloControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void shouldReturnHelloworldWhenHelloEndPointUsed() {
        var response = client.toBlocking().retrieve("/say_hello");
        assertEquals("Hello User", response);
    }

    @Test
    void shouldReturnProperStatusCodeWhenHelloEndPointUsed() {
        var response = client.toBlocking().exchange("/say_hello", String.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Hello User", response.getBody().get());
    }
}