package com.example.service;

import com.example.data.InMemoryStore;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import jakarta.inject.Inject;

import java.util.List;

public class BookService {
    InMemoryStore inMemoryStore;

    BookRepository bookRepository;

    public BookService(InMemoryStore inMemoryStore, BookRepository bookRepository) {
        this.inMemoryStore = inMemoryStore;
        this.bookRepository = bookRepository;
    }

    public List<Book> fetchBooks() {
        return inMemoryStore.BOOKS.values().stream().toList();
    }

    public Book fetchBook(int isbn) {
        return bookRepository.fetchBook(isbn);
    }

}
