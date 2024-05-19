package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String SERVER_ADDRESS = "localhost"; // Example server address
    private static final int PORT = 12323; // Example server port
    private static Scanner consoleInput = new Scanner(System.in);

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Prompt the user to enter username and password
            out.println(UserNamePrompt());
            out.println(PasswordPrompt());

            // Receive authentication result from server
            String response = in.readLine();

            // Check if user has authenticated account
            if (response.equals("Authentication successful.")) {
                System.out.println("\n--- Connected to server ---");
                System.out.println("\n--- Authentication successful ---");

                String userType = in.readLine();

                if (userType.equals("REGULAR_USER")) {
                    // Handle Regular User
                    HandleUser(out, in, socket);
                } else {
                    // Handle Librarian User
                    HandleLibrarian(out, in, socket);
                }
            } else {
                System.out.println("\n--- Authentication Failed ---");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            consoleInput.close();
        }
    }

    public static void HandleUser(PrintWriter out, BufferedReader in, Socket socket) throws IOException {
        int choice;
        do {
            displayUserMenu();
            choice = getChoice();

            // Handle user's choice
            switch (choice) {
                case 1:
                    // searching
                    System.out.print("\nEnter book title:\n");
                    String BookTitle = consoleInput.nextLine();
                    out.println("Client Menu");
                    out.println(choice);
                    out.println(BookTitle);
                    System.out.println(in.readLine());
                    break;
                case 2:
                    // Borrow a book
                    System.out.print("\nEnter book title:\n");
                    BookTitle = consoleInput.nextLine();
                    out.println("Client Menu");
                    out.println(choice);
                    out.println(BookTitle);
                    System.out.println(in.readLine());
                    break;
                case 3:
                    out.println("Client Menu");
                    out.println(choice);
                    String book;
                    while (!(book = in.readLine()).equals("END")) {
                        System.out.println(book);
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    socket.close();
                    System.exit(0);
                    break;
                default:
                    displayErrorMessage("Invalid choice");
            }
        } while (choice != 4);
    }

    public static void HandleLibrarian(PrintWriter out, BufferedReader in, Socket socket) throws IOException {
        int choice;
        do {
            displayLibrarianMenu();
            System.out.println("Enter your choice:");
            choice = getChoice();

            switch (choice) {
                case 1: // Add a new book
                    System.out.println("Enter the ISBN:");
                    String ISBN = consoleInput.nextLine();
                    System.out.println("Enter the book title:");
                    String title = consoleInput.nextLine();
                    System.out.println("Enter the author:");
                    String author = consoleInput.nextLine();
                    System.out.println("Enter the number of copies:");
                    int copies = Integer.parseInt(consoleInput.nextLine());

                    // Send details to the server
                    out.println("Librarian Menu");
                    out.println(choice);
                    out.println(ISBN);
                    out.println(title);
                    out.println(author);
                    out.println(copies);
                    // Wait for the server response
                    System.out.println(in.readLine());
                    break;
                case 2: // Remove a book
                    System.out.println("Enter the ISBN of the book to remove:");
                    ISBN = consoleInput.nextLine();
                    out.println("Librarian Menu");
                    out.println(choice);
                    out.println(ISBN);
                    System.out.println(in.readLine());
                    break;
                case 3: // Update book details
                    System.out.println("Enter the ISBN of the book to update:");
                    ISBN = consoleInput.nextLine();
                    System.out.println("Enter the new book title:");
                    title = consoleInput.nextLine();
                    System.out.println("Enter the new author:");
                    author = consoleInput.nextLine();
                    System.out.println("Enter the new number of copies:");
                    copies = Integer.parseInt(consoleInput.nextLine());

                    out.println("Librarian Menu");
                    out.println(choice);
                    out.println(ISBN);
                    out.println(title);
                    out.println(author);
                    out.println(copies);
                    System.out.println(in.readLine());
                    break;
                case 4: // View all books
                    out.println("Librarian Menu");
                    out.println(choice);
                    String book;
                    while (!(book = in.readLine()).equals("END")) {
                        System.out.println(book);
                    }
                    break;
                case 5: // Search for a book
                    System.out.print("\nEnter book title:\n");
                    String BookTitle = consoleInput.nextLine();
                    out.println("Librarian Menu");
                    out.println(choice);
                    out.println(BookTitle);
                    System.out.println(in.readLine());
                    break;

                case 6: // Exit
                    System.out.println("Exiting...");
                    socket.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    break;
            }
        } while (choice != 6);
    }

    public static void displayUserMenu() {
        System.out.println("===========================");
        System.out.println("Welcome to Library System");
        System.out.println("Regular User Menu:");
        System.out.println("===========================");
        System.out.println("1. Search for a Book");
        System.out.println("2. Borrow a Book");
        System.out.println("3. Show all LLM Book");
        System.out.println("4. Exit");
        System.out.print("\nEnter your choice: ");
    }

    public static void displayLibrarianMenu() {
        System.out.println("===========================");
        System.out.println("Welcome to Library System");
        System.out.println("Librarian Menu:");
        System.out.println("===========================");
        System.out.println("1. Add a new book");
        System.out.println("2. Remove a book");
        System.out.println("3. Update book details");
        System.out.println("4. View all books");
        System.out.println("5. Search for a book");
        System.out.println("6. Exit");

        System.out.print("Enter your choice: ");
    }

    public static int getChoice() throws IOException {
        while (!consoleInput.hasNextInt()) {
            consoleInput.next(); // consume the invalid input
            System.out.print("Invalid input. Please enter a number: ");
        }
        int choice = consoleInput.nextInt();
        consoleInput.nextLine(); // consume the newline character
        return choice;
    }

    private static void displayErrorMessage(String message) {
        System.out.println("Sorry, there's a problem: " + message);
    }

    public static String UserNamePrompt() {
        System.out.print("Enter username: ");
        return consoleInput.nextLine();
    }

    public static String PasswordPrompt() {
        System.out.print("Enter password: ");
        return consoleInput.nextLine();
    }
}
