package lms;

import java.util.Scanner;
import Server.DatabaseHandler;

public class Librarian extends User {
    private String LibrarianID;
    private String department;
    private DatabaseHandler dbHandler;  // Instance field for DatabaseHandler


    public Librarian(String LibrarianID, String department, String name, String email, String password, String userType) {
        super(name, email, password, userType);
        this.LibrarianID = LibrarianID;
        this.department = department;
        this.dbHandler = dbHandler;  // Initialize the DatabaseHandler instance

    }
    

    // Getters and setters

    public String getLibrarianID() {
        return LibrarianID;
    }

    public void setLibrarianID(String LibrarianID) {
        this.LibrarianID = LibrarianID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}