package com.example.repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.example.constant.AerospikeConstants;
import com.example.entity.Book;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class BookRepository {
    private final AerospikeClient aerospikeClient;


    @Inject
    public BookRepository(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    public Book fetchBook(int id) {
        Key key = new Key(AerospikeConstants.NAMESPACE, AerospikeConstants.BOOK_SET, String.valueOf(id));
        Record record = aerospikeClient.get(null, key);
        Book book = null;
        if (record != null) {
            book = getBook(record);
        }
        return book;
    }

    private Book getBook(Record record) {
        Map<String, Object> bins = record.bins;
        String title = bins.get("title").toString();
        String author = bins.get("author").toString();
        Long isbn = (Long) bins.get("isbn");
        Long noOfPages = (Long) bins.get("noOfPages");
        Long price = (Long) bins.get("price");
        return new Book(isbn.intValue(), title, author, noOfPages.intValue(), price);
    }
}
