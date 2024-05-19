package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lms.Book;
import lms.User;

public class DatabaseHandler {

    private static final String USERS_FILE = "User.txt";
    private static final String BOOKS_FILE = "Book.txt";

    // Lists to store users and books
    private List<User> users;
    private List<Book> books;

    public DatabaseHandler() {
        users = new ArrayList<>();
        books = new ArrayList<>();

        // Load data from text files
        loadUsers();
        loadBooks();
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String email = parts[2];
                String password = parts[1];
                String userType = parts[3];
                User user = new User(username, email, password, userType);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String ISBN = parts[0];
                String title = parts[1];
                String author = parts[2];
                String genre = parts[3];
                int numCopies = Integer.parseInt(parts[4]);
                Book book = new Book(ISBN, title, author, numCopies);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUName().matches(username) && user.getPassword().matches(password)) {
                return user; // Authentication successful
            }
        }
        return null; // Authentication failed
    }

    public String BookQuery(String Book) {
        for (Book b : books) {
            if (b.getTitle().matches(Book)) {
                return b.toString();
            }

        }
        return " -- Sorry we Don't have this Book --";
    }

    public String Borrow(String Title) {
        for (Book b : books) {
            if (b.getTitle().matches(Title)) {
                if (b.getAvaialableCopies() > 1) {
                    b.Borrow();
                    return "User has borrow this book sucssesfuly";
                } else {
                    return "sorry there's no avaliable copies you can't borrow this book.";
                }
            }
        }
        return "there's no such book in our library";

    }

    public List<Book> SearchBook() {
        return books;
    }

    public void addBook(String ISBN, String Title, String author, int copies) {
        Book newBook = new Book(ISBN, Title, author, copies);
        books.add(newBook);
        saveBooks(); // Method to write changes back to the books.txt file
    }

    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt", false))) {
            for (Book book : books) {
                bw.write(book.getISBN() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getAvaialableCopies());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save books: " + e.getMessage());
        }
    }
    public boolean removeBook(String ISBN) {
        Book toRemove = null;
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                toRemove = book;
                break;
            }
        }
        if (toRemove != null) {
            books.remove(toRemove);
            saveBooks();
            return true;
        }
        return false;
    }
    public boolean updateBookDetails(String ISBN, String newTitle, String newAuthor,  int newCopies) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setAvaialableCopies(newCopies);
                saveBooks();
                return true;
            }
        }
        return false;
    }

}
