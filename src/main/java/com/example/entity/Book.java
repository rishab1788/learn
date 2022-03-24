package com.example.entity;

public class Book {
    private final int isbn;
    private final String title;
    private final String author;
    private final int noOfPages;
    private final double price;

    public Book(int isbn, String title, String author, int noOfPages, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.noOfPages = noOfPages;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public double getPrice() {
        return price;
    }

    public int getIsbn() {
        return isbn;
    }

}
