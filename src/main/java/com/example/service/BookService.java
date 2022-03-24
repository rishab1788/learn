package com.example.service;

import com.example.entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> fetchBooks();

    public Book fetchBook(int isbn);

}
