package com.librarymanagement.view;

import com.librarymanagement.controller.BookController;
import com.librarymanagement.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class MainView extends JFrame {
    private BookController bookController;
    private JPanel booksPanel;

    public MainView() {
        bookController = new BookController();
        setTitle("Library Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        // Painel superior com botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        addButton(buttonPanel, "Add Book", e -> addBook());
        addButton(buttonPanel, "View Books", e -> updateBooksList());
        addButton(buttonPanel, "Rent Book", e -> rentBook());
        addButton(buttonPanel, "Return Book", e -> returnBook());
        addButton(buttonPanel, "Refresh", e -> updateBooksList());

        // Painel para exibir os livros
        booksPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        JScrollPane scrollPane = new JScrollPane(booksPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        updateBooksList(); // Popula os livros inicialmente
    }

    private void addButton(JPanel panel, String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        panel.add(button);
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog("Enter book title:");
        String author = JOptionPane.showInputDialog("Enter book author:");
        String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
        int year;
        try {
            year = Integer.parseInt(JOptionPane.showInputDialog("Enter book year:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid year format.");
            return;
        }
        String imagePath = JOptionPane.showInputDialog("Enter book image URL:");

        if (title != null && author != null && isbn != null && imagePath != null) {
            Book book = new Book(title, author, isbn, year, imagePath);
            bookController.addBook(book);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            updateBooksList();
        } else {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
        }
    }

    private void rentBook() {
        String isbn = JOptionPane.showInputDialog("Enter book ISBN to rent:");
        boolean success = bookController.rentBook(isbn);
        JOptionPane.showMessageDialog(this, success ? "Book rented successfully!" : "Book not available or not found.");
        updateBooksList();
    }

    private void returnBook() {
        String isbn = JOptionPane.showInputDialog("Enter book ISBN to return:");
        boolean success = bookController.returnBook(isbn);
        JOptionPane.showMessageDialog(this, success ? "Book returned successfully!" : "Book not found or not rented.");
        updateBooksList();
    }

    private void updateBooksList() {
        booksPanel.removeAll();
        for (Book book : bookController.getBooks()) {
            booksPanel.add(createBookPanel(book));
        }
        booksPanel.revalidate();
        booksPanel.repaint();
    }

    private JPanel createBookPanel(Book book) {
        JPanel bookPanel = new JPanel();
        bookPanel.setPreferredSize(new Dimension(200, 300));
        bookPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        bookPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(book.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bookPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        try {
            URL imageUrl = new URL(book.getImagePath());
            Image image = ImageIO.read(imageUrl);
            if (image != null) {
                ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 200, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
            } else {
                imageLabel.setText("Image not found");
            }
        } catch (MalformedURLException e) {
            imageLabel.setText("Invalid URL");
        } catch (IOException e) {
            imageLabel.setText("Error loading image");
        }

        bookPanel.add(imageLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel.add(new JLabel("Author: " + book.getAuthor()));
        infoPanel.add(new JLabel("ISBN: " + book.getIsbn()));
        infoPanel.add(new JLabel("Year: " + book.getYear()));
        infoPanel.add(new JLabel(book.isRented() ? "Status: Rented" : "Status: Available"));
        bookPanel.add(infoPanel, BorderLayout.SOUTH);

        return bookPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
        });
    }
}
