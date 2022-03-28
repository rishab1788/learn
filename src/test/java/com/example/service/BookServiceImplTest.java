package com.example.service;

import com.example.data.InMemoryStore;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@ExtendWith({MockitoExtension.class})
class BookServiceImplTest {

    BookService bookService;

    @Mock BookRepository mockBookRepository;

    @BeforeEach
    void setup() {
        bookService = new BookService(new InMemoryStore(),mockBookRepository);
    }

    @Test
    void shouldFetchBookWhenBookWithIdIsPresent() {
        Book book = getSampleBook();
        Mockito.when(mockBookRepository.fetchBook(1)).thenReturn(book);

        Book actual = bookService.fetchBook(1);

        assertEquals(actual, book);// ambiguity in actual and expected
        assertThat(actual).isEqualTo(book);
    }

    @Test
    void shouldNotFetchBookWhenBookWithIdIsNotPresent() {
        Mockito.when(mockBookRepository.fetchBook(2)).thenReturn(null);

        Book actual = bookService.fetchBook(2);

        assertThat(actual).isEqualTo(null);
    }

    private Book getSampleBook() {
        return new Book(1, "wings", "someone", 10, 5);
    }
}