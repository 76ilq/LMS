package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends JFrame {

    private static final String SERVER_ADDRESS = "localhost"; // Example server address
    private static final int PORT = 12323; // Example server port
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    private JPanel loginPanel, userPanel, librarianPanel;
    private CardLayout cardLayout;

    public ClientGUI() {
        // Setup main frame
        setTitle("Library Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Initialize panels
        loginPanel = new JPanel(new GridLayout(3, 2));
        userPanel = new JPanel();
        librarianPanel = new JPanel();

        // Add components to login panel
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        add(loginPanel, "login");
        add(userPanel, "user");
        add(librarianPanel, "librarian");

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                authenticate(username, password);
            }
        });
    }

    private void authenticate(String username, String password) {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(username);
            out.println(password);

            String response = in.readLine();
            if (response.equals("Authentication successful.")) {
                String userType = in.readLine();
                if (userType.equals("REGULAR_USER")) {
                    setupUserPanel();
                    cardLayout.show(getContentPane(), "user");
                } else {
                    setupLibrarianPanel();
                    cardLayout.show(getContentPane(), "librarian");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Authentication Failed", "Error", JOptionPane.ERROR_MESSAGE);
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setupUserPanel() {
        userPanel.setLayout(new GridLayout(5, 1));

        JButton searchButton = new JButton("Search for a Book");
        JButton borrowButton = new JButton("Borrow a Book");
        JButton showAllButton = new JButton("Show all LLM Book");
        JButton exitButton = new JButton("Exit");

        userPanel.add(searchButton);
        userPanel.add(borrowButton);
        userPanel.add(showAllButton);
        userPanel.add(new JLabel());
        userPanel.add(exitButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = JOptionPane.showInputDialog("Enter book title:");
                if (bookTitle != null) {
                    out.println("Client Menu");
                    out.println(1);
                    out.println(bookTitle);
                    try {
                        JOptionPane.showMessageDialog(userPanel, in.readLine());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = JOptionPane.showInputDialog("Enter book title:");
                if (bookTitle != null) {
                    out.println("Client Menu");
                    out.println(2);
                    out.println(bookTitle);
                    try {
                        JOptionPane.showMessageDialog(userPanel, in.readLine());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("Client Menu");
                out.println(3);
                try {
                    String book;
                    StringBuilder books = new StringBuilder();
                    while (!(book = in.readLine()).equals("END")) {
                        books.append(book).append("\n");
                    }
                    JOptionPane.showMessageDialog(userPanel, books.toString());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    private void setupLibrarianPanel() {
        librarianPanel.setLayout(new GridLayout(7, 1));

        JButton addButton = new JButton("Add a new book");
        JButton removeButton = new JButton("Remove a book");
        JButton updateButton = new JButton("Update book details");
        JButton viewAllButton = new JButton("View all books");
        JButton searchButton = new JButton("Search for a book");
        JButton exitButton = new JButton("Exit");

        librarianPanel.add(addButton);
        librarianPanel.add(removeButton);
        librarianPanel.add(updateButton);
        librarianPanel.add(viewAllButton);
        librarianPanel.add(searchButton);
        librarianPanel.add(new JLabel());
        librarianPanel.add(exitButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField isbnField = new JTextField();
                JTextField titleField = new JTextField();
                JTextField authorField = new JTextField();
                JTextField copiesField = new JTextField();
                Object[] message = {
                    "ISBN:", isbnField,
                    "Title:", titleField,
                    "Author:", authorField,
                    "Copies:", copiesField
                };

                int option = JOptionPane.showConfirmDialog(librarianPanel, message, "Add a new book", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    out.println("Librarian Menu");
                    out.println(1);
                    out.println(isbnField.getText());
                    out.println(titleField.getText());
                    out.println(authorField.getText());
                    out.println(copiesField.getText());
                    try {
                        JOptionPane.showMessageDialog(librarianPanel, in.readLine());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to remove:");
                if (isbn != null) {
                    out.println("Librarian Menu");
                    out.println(2);
                    out.println(isbn);
                    try {
                        JOptionPane.showMessageDialog(librarianPanel, in.readLine());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField isbnField = new JTextField();
                JTextField titleField = new JTextField();
                JTextField authorField = new JTextField();
                JTextField copiesField = new JTextField();
                Object[] message = {
                    "ISBN:", isbnField,
                    "New Title:", titleField,
                    "New Author:", authorField,
                    "New Copies:", copiesField
                };

                int option = JOptionPane.showConfirmDialog(librarianPanel, message, "Update book details", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    out.println("Librarian Menu");
                    out.println(3);
                    out.println(isbnField.getText());
                    out.println(titleField.getText());
                    out.println(authorField.getText());
                    out.println(copiesField.getText());
                    try {
                        JOptionPane.showMessageDialog(librarianPanel, in.readLine());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("Librarian Menu");
                out.println(4);
                try {
                    String book;
                    StringBuilder books = new StringBuilder();
                    while (!(book = in.readLine()).equals("END")) {
                        books.append(book).append("\n");
                    }
                    JOptionPane.showMessageDialog(librarianPanel, books.toString());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = JOptionPane.showInputDialog("Enter book title:");
                if (bookTitle != null) {
                    out.println("Librarian Menu");
                    out.println(5);
                    out.println(bookTitle);
                    try {
                        JOptionPane.showMessageDialog(librarianPanel, in.readLine());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientGUI client = new ClientGUI();
            client.setVisible(true);
        });
    }
}
