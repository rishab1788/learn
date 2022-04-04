package com.example.repository;

import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.mapper.tools.IAeroMapper;
import com.example.entity.Book;
import com.example.entity.aerodb.BookEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

import static com.example.constant.AerospikeConstants.AUTHOR_BIN;
import static com.example.constant.AerospikeConstants.PRICE_BIN;

@Singleton
public class BookRepository {
    private final IAeroMapper aerospikeMapper;

    @Inject
    public BookRepository(IAeroMapper aerospikeMapper) {
        this.aerospikeMapper = aerospikeMapper;
    }

    public Book fetchBook(int id) {
        BookEntity bookEntity = aerospikeMapper.read(BookEntity.class, id);
        return bookEntity.getBook();
    }

    public List<Book> fetchBooks() {
        List<Book> books = new ArrayList<>();
        List<BookEntity> bookEntities = aerospikeMapper.scan(BookEntity.class);
        bookEntities.forEach(bookEntity -> books.add(bookEntity.getBook()));
        return books;
    }

    public List<Book> fetchBookByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        List<BookEntity> bookEntities = aerospikeMapper.query(BookEntity.class, Filter.equal(AUTHOR_BIN, author));
        bookEntities.forEach(bookEntity -> books.add(bookEntity.getBook()));
        return books;
    }

    public List<Book> addBook(BookEntity bookEntity) {
        aerospikeMapper.save(bookEntity);
        return fetchBooks();
    }

    public List<Book> fetchBookOfAuthorInPriceRange(String author, Long fromRange, Long toRange) {
        List<Book> books = new ArrayList<>();
        ScanPolicy policy = new ScanPolicy(aerospikeMapper.getScanPolicy(BookEntity.class));
        policy.filterExp = Exp.build(
                Exp.and(
                        Exp.eq(Exp.stringBin(AUTHOR_BIN), Exp.val(author)),
                        Exp.ge(Exp.intBin(PRICE_BIN), Exp.val(fromRange)),
                        Exp.lt(Exp.intBin(PRICE_BIN), Exp.val(toRange))));
        List<BookEntity> bookEntities = aerospikeMapper.scan(policy, BookEntity.class);
        bookEntities.forEach(bookEntity -> books.add(bookEntity.getBook()));
        return books;
    }
}
