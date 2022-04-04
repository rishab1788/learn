package com.example.controllers;

import com.example.entity.Book;
import com.example.entity.aerodb.BookEntity;
import com.example.service.BookService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class BookController {


    public static final String BOOKS_API = "books";
    public static final String BOOKS_AUTHOR_API = "books/author";
    private static final String IN_MEMORY_BOOKS_API = "inmemory/books";
    private static final String FILTER = "filter";
    private static final String AUTHOR_NAME_CURLY_BRACE = "{authorName}";
    private static final String ISBN_CURLY_BRACE = "{isbn}";

    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Inject
    private BookService bookService;

    @Get(IN_MEMORY_BOOKS_API)
    public List<Book> getBooksFromInMemory() {
        List<Book> bookList = bookService.fetchBooksFromInMemory();
        LOG.debug("Number of Fetched Books : " + bookList.size());
        return bookList;
    }

    @Get(IN_MEMORY_BOOKS_API + "/" + ISBN_CURLY_BRACE)
    public Book getBookFromInMemory(@PathVariable Integer isbn) {
        return bookService.fetchBookFromInMemory(isbn);
    }

    @Get(BOOKS_API)
    public List<Book> getBooks() {
        List<Book> bookList = bookService.fetchBooks();
        LOG.debug("Number of Fetched Books : " + bookList.size());
        return bookList;
    }

    @Get(BOOKS_API + "/" + ISBN_CURLY_BRACE)
    public Book getBook(@PathVariable Integer isbn) {
        return bookService.fetchBook(isbn);
    }

    @Get(BOOKS_AUTHOR_API + "/" + AUTHOR_NAME_CURLY_BRACE)
    public List<Book> getBookByAuthor(@PathVariable String authorName) {
        return bookService.fetchBookByAuthor(authorName);
    }

    @Get(BOOKS_AUTHOR_API + "/" + AUTHOR_NAME_CURLY_BRACE + "/" + FILTER)
    public List<Book> getBookByAuthorPriceRange(@PathVariable String authorName, @QueryValue Long fromPrice, @QueryValue Long toPrice) {
        return bookService.fetchBookByAuthorInPriceRange(authorName, fromPrice, toPrice);
    }

    @Post(BOOKS_API)
    @Status(HttpStatus.CREATED)
    public List<Book> addBook(@RequestBody BookEntity bookEntity) {
        LOG.debug("Add book request for  : " + bookEntity);
        return bookService.addBook(bookEntity);
    }
}
