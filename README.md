# Library Management System (LMS)

## Description:
The Library Management System (LMS) is a Java-based application designed to facilitate the management of library resources, including books and user accounts. The system follows a client-server architecture, allowing users to interact with the library database through a user-friendly interface.

## Features:
- **User Authentication:** Users can authenticate themselves using their username and password to access the system.
- **Role-Based Access Control:** Different user roles (regular users and librarians) have access to different functionalities within the system.
- **Book Management:** Users can search for books, borrow books, and view all available books in the library.
- **Librarian Functions:** Librarians have additional functionalities such as adding new books, removing books, and updating book details.
- **Multi-Threaded Server:** The server can handle multiple client requests simultaneously using multi-threading.

## Project Structure:
The project consists of three main packages:
1. **Server:** Contains server-related classes, including the main server class, client handler class, and database handler class.
2. **Client:** Contains the client application class for user interaction.
3. **LMS:** Contains additional classes such as the Book and User classes.

## Installation and Usage:
1. **Clone the Repository:**
   ```bash
   git clone <https://github.com/76ilq/LMS/tree/main>
   ```

2. **Open the Project:**
   - Open the project in your preferred Java IDE.

3. **Run the Server:**
   - Navigate to the `ServerGUI` package and run the `ServerGUI.java` file to start the server.

4. **Run the Client:**
   - Navigate to the `ClientGUI` package and run the `ClientGUI.java` file to start the client application.

5. **User Authentication:**
   - Enter your username and password to authenticate and access the system.

6. **Interact with the System:**
   - Explore the menu options to perform various library-related tasks such as searching for books, borrowing books, etc.

7. **Exit the Application:**
   - Exit the client application by selecting the appropriate option from the menu.
   - Stop the server by terminating the server application.




---
