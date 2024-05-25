package lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LMS {

    List<Book> books = new ArrayList<>();
    HashMap<Book, Student> borrowedBooks = new HashMap<>();

    /**


     * @param book
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**

     *
     * @param book
     *
     */
    public boolean removeBook(Book book) {
        boolean removed = false;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).equals(book)) {
                books.remove(i);
                removed = true;
                break;
            }
        }

        return removed;
    }

    /**
     *
     *
     * @param book
     * @param student
     *
     */
    public boolean borrowBook(Book book, Student student) {
        boolean borrowed = false;

        if (books.contains(book) && !borrowedBooks.containsKey(book)) {
            borrowedBooks.put(book, student);
            borrowed = true;
        }

        return borrowed;
    }

    /**
     *
     *
     * @param book
     *
     */
    public boolean returnBook(Book book) {
        boolean returned = false;

        if (borrowedBooks.containsKey(book)) {
            borrowedBooks.remove(book);
            returned = true;
        }

        return returned;
    }

    /**
     *
     *
     * @param filePath
     */
    public void saveState(String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Book book : books) {
                writer.println(book.getTitle() + "," + book.getAuthor());
            }

            for (Map.Entry<Book, Student> entry : borrowedBooks.entrySet()) {
                writer.println(entry.getKey().getTitle() + "," + entry.getKey().getAuthor() + "," + entry.getValue().getName() + "," + entry.getValue().getSurname() + "," + entry.getValue().getPersonalNumber());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *
     * @param filePath
     *
     */
    public boolean loadState(String filePath) {
        boolean loaded = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 2) {
                    Book book = new Book(parts[0], parts[1]);
                    books.add(book);
                } else if (parts.length == 5) {
                    Book book = new Book(parts[0], parts[1]);
                    Student student = new Student(parts[2], parts[3], parts[4]);
                    borrowedBooks.put(book, student);
                }
            }

            loaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loaded;
    }

}