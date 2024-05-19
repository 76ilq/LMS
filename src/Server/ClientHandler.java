package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lms.Book;
import lms.User;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private DatabaseHandler databaseHandler;
    private boolean isRunning; 

    public ClientHandler(Socket clientSocket, DatabaseHandler databaseHandler) {
        this.clientSocket = clientSocket;
        this.databaseHandler = databaseHandler;
        this.isRunning = true;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            // Read input from the client and process it
            String inputLine;

            // Read username and password for authentication
            String username = in.readLine();
            String password = in.readLine();

            // Authenticate user
            User authenticatedUser = AccountAuth(username, password);

            if (authenticatedUser != null) {
                out.println("Authentication successful.");
                out.println(authenticatedUser.getuserType());

                // Keep reading client input until null is received or running flag is false
                while (((inputLine = in.readLine()) != null )&& isRunning) {
                    // Process client input and respond accordingly
                    if (inputLine.equals("Client Menu")) {
                        // Read client's choice
                        int choice = Integer.parseInt(in.readLine());
                        switch (choice) {
                            case 1:
                                String Title = in.readLine();
                                String Answer = BookQuery(Title);
                                out.println(Answer);
                                break;
                            case 2:
                                Title = in.readLine();
                                Answer = databaseHandler.Borrow(Title);
                                out.println(Answer);
                                break;
                            case 3:

                                List<Book> allBooks = databaseHandler.SearchBook();
                                for (Book book : allBooks) {
                                    out.println(book.toString());
                                }
                                out.println("END"); // Signal the end of book lis
                                break;
                            default:
                                out.println("Invalid choice.");
                                break;
                        }
                    } else if (inputLine.equals("Librarian Menu")) {
                        int choice = Integer.parseInt(in.readLine());
                        switch (choice) {
                            case 1: // add book
                                String ISBN = in.readLine();
                                String bookTitle = in.readLine();
                                String author = in.readLine();
                                int copies = Integer.parseInt(in.readLine());
                                databaseHandler.addBook(ISBN, bookTitle, author, copies);
                                out.println("Book added successfully.");
                                break;
                            case 2: // Remove book
                                ISBN = in.readLine();
                                boolean result = databaseHandler.removeBook(ISBN);
                                if (result) {
                                    out.println("Book removed successfully.");
                                } else {
                                    out.println("Failed to remove book.");
                                }
                                break;
                            case 3: // Update book
                                ISBN = in.readLine();
                                bookTitle = in.readLine();
                                author = in.readLine();
                                copies = Integer.parseInt(in.readLine());
                                result = databaseHandler.updateBookDetails(ISBN, bookTitle, author, copies);
                                if (result) {
                                    out.println("Book details updated successfully.");
                                } else {
                                    out.println("Failed to update book details.");
                                }
                                break;
                            case 4: // View books
                                List<Book> allBooks = databaseHandler.SearchBook();
                                for (Book book : allBooks) {
                                    out.println(book.toString());
                                }
                                out.println("END"); // end of the book list
                                break;
                            case 5:
                                String Title = in.readLine();
                                String Answer = BookQuery(Title);
                                out.println(Answer);
                                break;

                            case 6:
                                out.println("Exiting...");
                                break;
                            default:
                                out.println("Invalid command.");
                                break;
                        }
                    } else {
                        out.println("Invalid command.");
                    }
                }
            } else {
                out.println("Authentication Failed.");
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private User AccountAuth(String user, String pass) {
        // Authenticate the user using the DatabaseHandler
        return databaseHandler.authenticateUser(user, pass);
    }

    private String BookQuery(String book) {
        return databaseHandler.BookQuery(book);
    }
    
        public void stop() {
        isRunning = false;
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
