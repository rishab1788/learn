package com.example.service;

import com.example.data.InMemoryStore;
import com.example.entity.Book;
import io.micronaut.runtime.context.scope.Refreshable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;


@Refreshable
@Singleton
public class BookServiceImpl implements BookService {

    @Inject
    InMemoryStore inMemoryStore;

    public List<Book> fetchBooks(){
        return inMemoryStore.BOOKS.values().stream().toList();
    }

    @Override
    public Book fetchBook(int isbn) {
        return inMemoryStore.BOOKS.get(isbn);
    }
}
