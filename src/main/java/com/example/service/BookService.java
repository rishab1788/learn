package com.example.service;

import com.example.data.InMemoryStore;
import com.example.entity.Book;
import com.example.entity.aerodb.BookEntity;
import com.example.repository.BookRepository;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Singleton
public class BookService {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);
    private final InMemoryStore inMemoryStore;
    private final BookRepository bookRepository;

    public BookService(InMemoryStore inMemoryStore, BookRepository bookRepository) {
        this.inMemoryStore = inMemoryStore;
        this.bookRepository = bookRepository;
    }

    public List<Book> fetchBooksFromInMemory() {
        LOG.debug("fetching books from in memory db");
        return inMemoryStore.BOOKS.values().stream().toList();
    }

    public Book fetchBookFromInMemory(int isbn) {
        LOG.debug("fetching book from in memory db with isbn");
        return inMemoryStore.BOOKS.get(isbn);
    }

    public List<Book> fetchBooks() {
        LOG.debug("fetching list of books from aerospike db");
        return bookRepository.fetchBooks();
    }

    public Book fetchBook(int isbn) {
        LOG.debug("fetching list of books from aerospike db with isbn");
        return bookRepository.fetchBook(isbn);
    }

    public List<Book> fetchBookByAuthor(String authorName) {
        LOG.debug("fetching list of books from aerospike db with author name");
        return bookRepository.fetchBookByAuthor(authorName);
    }

    public List<Book> addBook(BookEntity bookEntity) {
        LOG.debug("adding book to aerospike db");
        return bookRepository.addBook(bookEntity);
    }

    public List<Book> fetchBookByAuthorInPriceRange(String author, Long fromPrice, Long toPrice) {
        return bookRepository.fetchBookOfAuthorInPriceRange(author, fromPrice, toPrice);
    }
}
