package com.example.controllers;

import com.aerospike.mapper.tools.IAeroMapper;
import com.example.aerospike.configuration.AerospikeConfiguration;
import com.example.aerospike.configuration.AerospikeConfigurationProperties;
import com.example.data.InMemoryStore;
import com.example.entity.Book;
import com.example.entity.aerodb.BookEntity;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        return new Book(1001, "sample_book", "sample_author", 10, 100);
    }

    private BookEntity getSampleBookEntity() {
        return new BookEntity(1001, "sample_book", "sample_author", 10, 100, 200);
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
    void shouldCreateBookWhenBookPostApiIsUsed() {
        var response = client.toBlocking().exchange(HttpRequest.POST("books", getSampleBookEntity()));

        HttpStatus actual = response.getStatus();

        assertThat(actual.getCode()).isEqualTo(HttpStatus.CREATED.getCode());
        aerospikeMapper.delete(getSampleBookEntity());
    }

    @Test
    void shouldFetchAllBooksOfAuthorWhenAuthorApiIsUsed() {
        BookEntity bookEntity = getSampleBookEntity();
        aerospikeMapper.save(bookEntity);
        var response = client.toBlocking().exchange("books/author/" + bookEntity.getAuthor(), Book.class);

        HttpStatus actualStatus = response.getStatus();
        Book actualBook = response.getBody().get();

        assertThat(actualStatus.getCode()).isEqualTo(HttpStatus.OK.getCode());
        assertThat(actualBook.getIsbn()).isEqualTo(getSampleBookEntity().getIsbn());
        aerospikeMapper.delete(bookEntity);
    }

    @Test
    void shouldReturnAllPresentBook() {
        var response = client.toBlocking().exchange("books/", List.class);

        assertEquals(response.getStatus(), HttpStatus.OK);
    }
}