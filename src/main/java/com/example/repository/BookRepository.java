package com.example.repository;

import com.aerospike.client.IAerospikeClient;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.mapper.tools.IAeroMapper;
import com.example.entity.Book;
import com.example.entity.aerodb.BookEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static com.example.constant.AerospikeConstants.*;

@Singleton
public class BookRepository {
    private final IAerospikeClient aerospikeClient;

    private final IAeroMapper aerospikeMapper;

    @Inject
    public BookRepository(IAerospikeClient aerospikeClient, IAeroMapper aerospikeMapper) {
        this.aerospikeMapper = aerospikeMapper;
        this.aerospikeClient = aerospikeClient;
    }

    public Book fetchBook(int id) {
        BookEntity bookEntity = aerospikeMapper.read(BookEntity.class, id);
        return Book.createBook(bookEntity);
    }

    public List<Book> fetchBook() {
        Statement statement = new Statement();
        statement.setNamespace(NAMESPACE);
        statement.setSetName(BOOK_SET);
        RecordSet recordSet = aerospikeClient.query(null, statement);
        try {
            List<Book> books = new ArrayList<>();
            recordSet.forEach(record -> {
                BookEntity bookEntity = aerospikeMapper.getMappingConverter().convertToObject(BookEntity.class, record.record);
                books.add(Book.createBook(bookEntity));
            });
            return books;
        } finally {
            recordSet.close();
        }
    }

    public List<Book> fetchBookByAuthor(String author) {
        Statement statement = new Statement();
        statement.setNamespace(NAMESPACE);
        statement.setSetName(BOOK_SET);
        statement.setFilter(Filter.equal(AUTHOR_BIN, author));
        RecordSet recordSet = aerospikeClient.query(null, statement);
        try {
            List<Book> books = new ArrayList<>();
            recordSet.forEach(record -> {
                BookEntity bookEntity = aerospikeMapper.getMappingConverter().convertToObject(BookEntity.class, record.record);
                books.add(Book.createBook(bookEntity));
            });
            return books;
        } finally {
            recordSet.close();
        }
    }

    public List<Book> addBook(BookEntity bookEntity) {
        aerospikeMapper.save(bookEntity);
        return fetchBook();
    }
}
