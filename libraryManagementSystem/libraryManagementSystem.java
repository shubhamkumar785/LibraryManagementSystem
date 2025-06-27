
import java.io.*;
import java.util.*;

public class libraryManagementSystem {

    private static final String FILE_NAME = "library_data.ser";
    private static ArrayList<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n----- Library Management System -----");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addBook(sc);
                    break;
                case 2:
                    deleteBook(sc);
                    break;
                case 3:
                    searchBook(sc);
                    break;
                case 4:
                    displayBooks();
                    break;
                case 5:
                    saveData();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    private static void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // clear buffer

        Book book = new Book(id, title, author, quantity);
        bookList.add(book);
        System.out.println("Book added successfully!");

        // Show all books immediately
        displayBooks();
    }

    private static void deleteBook(Scanner sc) {
        System.out.print("Enter Book ID to delete: ");
        String id = sc.nextLine();
        boolean removed = bookList.removeIf(book -> book.getId().equalsIgnoreCase(id));
        if (removed) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found!");
        }
    }

    private static void searchBook(Scanner sc) {
        System.out.print("Enter Book ID to search: ");
        String id = sc.nextLine();
        boolean found = false;

        System.out.println("\nSearching for book...");
        for (Book book : bookList) {
            if (book.getId().equalsIgnoreCase(id)) {
                System.out.println("✅ Book found:");
                System.out.println(book);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("❌ Book not found in the library.");
        }
    }

    private static void displayBooks() {
        if (bookList.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("All Books:");
            for (Book book : bookList) {
                System.out.println(book);
            }
        }
    }

    private static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(bookList);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                bookList = (ArrayList<Book>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
        }
    }
}
