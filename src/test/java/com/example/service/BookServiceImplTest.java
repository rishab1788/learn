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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@ExtendWith({MockitoExtension.class})
class BookServiceImplTest {

    private static final String SOMEONE = "SOMEONE";
    BookService bookService;
    @Mock
    BookRepository mockBookRepository;

    @BeforeEach
    void setup() {
        bookService = new BookService(new InMemoryStore(), mockBookRepository);
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

    @Test
    void shouldFetchBooksWhenBookWithAuthorNameIsPresent() {
        Mockito.when(mockBookRepository.fetchBookByAuthor(SOMEONE)).thenReturn(Collections.singletonList(getSampleBook()));

        List<Book> actual = bookService.fetchBookByAuthor(SOMEONE);

        assertThat(actual.get(0).getTitle()).isEqualTo(getSampleBook().getTitle());
        assertThat(actual.get(0).getIsbn()).isEqualTo(getSampleBook().getIsbn());
    }

    @Test
    void shouldNotFetchBooksWhenBookWithAuthorNameIsNotPresent() {
        Mockito.when(mockBookRepository.fetchBookByAuthor(SOMEONE)).thenReturn(null);

        List<Book> actual = bookService.fetchBookByAuthor(SOMEONE);

        assertThat(actual).isEqualTo(null);
    }

    @Test
    void shouldFetchBooksIfGivenPriceRangeofAuthorBooksPresent() {
        Mockito.when(mockBookRepository.fetchBookOfAuthorInPriceRange(SOMEONE, 10L, 5000L)).thenReturn(List.of(getSampleBook()));

        List<Book> actual = bookService.fetchBookByAuthorInPriceRange(SOMEONE, 10L, 5000L);

        assertThat(actual.get(0).getIsbn()).isEqualTo(getSampleBook().getIsbn());
    }

    @Test
    void shouldNotFetchBooksIfGivenPriceRangeOfAuthorBooksNotPresent() {
        Mockito.when(mockBookRepository.fetchBookOfAuthorInPriceRange(SOMEONE, 10L, 5000L))
                .thenReturn(new ArrayList<>());

        List<Book> actual = bookService.fetchBookByAuthorInPriceRange(SOMEONE, 10L, 5000L);

        assertThat(actual).isEqualTo(new ArrayList<>());
    }

    private Book getSampleBook() {
        return new Book(1, "wings", SOMEONE, 10, 5);
    }
}