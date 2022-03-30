package com.example.controllers;

import com.example.entity.Book;
import com.example.service.BookService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class BookController {

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Inject
    private BookService bookService;

    @Get("inmemory/books/")
    public List<Book> getBooks() {
        List<Book> bookList = bookService.fetchBooks();
        LOG.debug("Number of Fetched Books : " + bookList.size());
        return bookList;
    }

    @Get("inmemory/books/{isbn}")
    public Book getBookFromInMemory(@PathVariable Integer isbn) {
        return bookService.fetchBookFromInMemory(isbn);
    }

    @Get("/books/{isbn}")
    public Book getBook(@PathVariable Integer isbn) {
        return bookService.fetchBook(isbn);
    }

    @Get("books/author/{authorName}")
    public List<Book> getBookByAuthor(@PathVariable String authorName) {
        return bookService.fetchBookByAuthor(authorName);
    }
}
