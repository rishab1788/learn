package com.example.entity.aerodb;

import com.aerospike.mapper.annotations.AerospikeBin;
import com.aerospike.mapper.annotations.AerospikeKey;
import com.aerospike.mapper.annotations.AerospikeRecord;
import com.aerospike.mapper.annotations.ParamFrom;
import com.example.constant.AerospikeConstants;

@AerospikeRecord(namespace = AerospikeConstants.NAMESPACE, set = AerospikeConstants.BOOK_SET)
public class BookEntity {
    @AerospikeKey
    @AerospikeBin(name = "id")
    public final int id;
    @AerospikeBin
    public final String title;
    @AerospikeBin
    public final String author;
    @AerospikeBin
    public final int isbn;
    @AerospikeBin
    public final int noOfPages;
    @AerospikeBin
    public final double price;

    public BookEntity(@ParamFrom(AerospikeConstants.ID) int id,
                      @ParamFrom(AerospikeConstants.AUTHOR_BIN) String author,
                      @ParamFrom(AerospikeConstants.TITLE_BIN) String title,
                      @ParamFrom(AerospikeConstants.ISBN_BIN) int isbn,
                      @ParamFrom(AerospikeConstants.NO_OF_PAGES_BIN) int noOfPages,
                      @ParamFrom(AerospikeConstants.PRICE_BIN) double price) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.noOfPages = noOfPages;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getIsbn() {
        return isbn;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public double getPrice() {
        return price;
    }
}
