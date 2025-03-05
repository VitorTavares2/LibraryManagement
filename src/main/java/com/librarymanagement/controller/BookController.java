package com.librarymanagement.controller;

import com.librarymanagement.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    private List<Book> books;

    public BookController() {
        books = new ArrayList<>();
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "1234", 1925, "https://cdn.kobo.com/book-images/2411acbb-9daa-43fb-a5a2-a9aec064e17e/1200/1200/False/the-great-gatsby-238.jpg"));
        books.add(new Book("1984", "George Orwell", "5678", 1949, "https://m.media-amazon.com/images/I/61M9jDcsl2L._AC_UF1000,1000_QL80_.jpg"));
        books.add(new Book("Pride and Prejudice", "Jane Austen", "9101", 1813, "https://rukminim2.flixcart.com/image/850/1000/xif0q/book/f/s/1/pride-prejudice-original-imagy32ephewfga2.jpeg?q=90&crop=false"));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "1121", 1951, "https://m.media-amazon.com/images/I/8125BDk3l9L._AC_UF1000,1000_QL80_.jpg"));
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean rentBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isRented()) {
                book.setRented(true);
                return true;
            }
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isRented()) {
                book.setRented(false);
                return true;
            }
        }
        return false;
    }
}
