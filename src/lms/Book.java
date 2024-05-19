
package lms;

public class Book {
    private String ISBN;
    private static int AvaialableCopies;
    private String author;
    private String Title;

    public Book(String ISBN, String Title, String author, int avaialableCopies) {
        this.ISBN = ISBN;
        this.author = author;
        this.Title = Title;
        this.AvaialableCopies = avaialableCopies;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public static int getAvaialableCopies() {
        return AvaialableCopies;
    }

    public static void setAvaialableCopies(int AvaialableCopies) {
        Book.AvaialableCopies = AvaialableCopies;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void Borrow() {
        this.AvaialableCopies --;}

    public void Return() {
    }

    @Override
    public String toString() {
        return "Book{" + "ISBN=" + ISBN + ", author=" + author + ", Title=" + Title + '}' + "\n\nAvailable copies = "+AvaialableCopies+"\n\n";
    }

}
