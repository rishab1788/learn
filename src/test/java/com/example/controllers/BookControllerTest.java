package com.example.controllers;

import com.aerospike.mapper.tools.IAeroMapper;
import com.example.aerospike.configuration.AerospikeConfiguration;
import com.example.aerospike.configuration.AerospikeConfigurationProperties;
import com.example.data.InMemoryStore;
import com.example.entity.Book;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class BookControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    private InMemoryStore inMemoryStore;

    @Inject
    private AerospikeConfiguration aerospikeConfiguration;

    @Inject
    private AerospikeConfigurationProperties aerospikeConfigurationProperties;

    @Inject
    private IAeroMapper aerospikeMapper;

    @BeforeEach
    void setup() {
    }

    private Book getSampleBook() {
        return new Book(1001, "sample book", "sample author", 10, 100);
    }

    @Test
    void shouldReturnBooksWhenGetBooksApiUsed() {

        var response = client.toBlocking().exchange("inmemory/books", List.class);

        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void shouldReturnBookWhenBookApiWithIdIsUsed() {
        Book book = getSampleBook();
        inMemoryStore.BOOKS.put(book.getIsbn(), book);

        var response = client.toBlocking().exchange("inmemory/books/" + book.getIsbn(), Book.class);
        var fetchedBook = response.getBody().get();

        assertEquals(book.getIsbn(), fetchedBook.getIsbn());
        assertEquals(book.getTitle(), fetchedBook.getTitle());
    }

    @Test
    void shouldReturnAllPresentBook() {
        var response = client.toBlocking().exchange("books/", List.class);

        assertEquals(response.getStatus(), HttpStatus.OK);
    }
}