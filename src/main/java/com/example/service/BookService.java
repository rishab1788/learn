package com.example.service;

import com.example.data.InMemoryStore;
import com.example.entity.Book;
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

    public List<Book> fetchBooks() {
        return inMemoryStore.BOOKS.values().stream().toList();
    }

    public Book fetchBookFromInMemory(int isbn) {
        return inMemoryStore.BOOKS.get(isbn);
    }

    public Book fetchBook(int isbn) {
        return bookRepository.fetchBook(isbn);
    }

    public List<Book> fetchBookByAuthor(String authorName) {
        return bookRepository.fetchBookByAuthor(authorName);
    }
}
