package com.example.controllers;

import com.example.entity.Book;
import com.example.service.BookService;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import jakarta.inject.Inject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;

@Controller
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Inject
    BookService bookService;

    @Get("/books/")
    public List<Book> getBooks() {
        List<Book> bookList = bookService.fetchBooks();
        LOG.debug("Number of Fetched Books : " + bookList.size() );
        return bookList;
    }

    @Get("/book/{isbn}")
    public Book getBook(@PathVariable Integer isbn) {
        return bookService.fetchBook(isbn);
    }
}
