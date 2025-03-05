package com.librarymanagement.model;

public class Book {
    private String title;
    private String author;
    private String isbn;
    private int year;
    private String imagePath;
    private boolean rented;

    public Book(String title, String author, String isbn, int year, String imagePath) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.imagePath = imagePath;
        this.rented = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getYear() {
        return year;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
