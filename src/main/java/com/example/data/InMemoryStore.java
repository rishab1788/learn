package com.example.data;

import com.example.entity.Book;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryStore {
    public final Map<Integer, Book> BOOKS;

    public InMemoryStore() {
        this.BOOKS = new HashMap<>();
        Book book1 = new Book(0001, "Wings of fire", "APJ", 100, 900);
        Book book2 = new Book(0002, "Art of wonders", "Kalam", 102, 230);
        Book book3 = new Book(0003, "XP practice", "Tw", 103, 1900);

        this.BOOKS.put(book1.getIsbn(),book1);
        this.BOOKS.put(book2.getIsbn(),book2);
        this.BOOKS.put(book3.getIsbn(),book3);
    }


}
