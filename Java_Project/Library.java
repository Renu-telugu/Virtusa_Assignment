import java.util.ArrayList;
import java.util.HashMap;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    private int bookIdCounter = 1;
    private int userIdCounter = 1;

    public Library() {
        // default users
        for (int i = 1; i <= 5; i++) {
            users.add(new User(i, "u" + i));
        }
        userIdCounter = 6;

        // default books
        books.add(new Book(bookIdCounter++, "One Arranged Murder", "Chetan Bhagat", 5));
        books.add(new Book(bookIdCounter++, "Think Straight", "Darius Foroux", 2));
        books.add(new Book(bookIdCounter++, "Atomic Habits", "James Clear", 3));
        books.add(new Book(bookIdCounter++, "The Alchemist", "Paulo Coelho", 4));
        books.add(new Book(bookIdCounter++, "Can we be stangers again?", "Shrijeet Shandilya", 2));
    }

    // add book or increase stock
    public void addBook(String title, String author, int count) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title) &&
                b.getAuthor().equalsIgnoreCase(author)) {

                b.increaseStock(count);
                System.out.println("Book already exists. Stock updated: " + b.getStock());
                return;
            }
        }

        Book newBook = new Book(bookIdCounter++, title, author, count);
        books.add(newBook);
        System.out.println("Book added. Book ID: " + newBook.getId());
    }

    // update book details
    public void updateBook(int id, String newTitle, String newAuthor) {
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);

            if (b.getId() == id) {
                books.set(i, new Book(id, newTitle, newAuthor, b.getStock()));
                System.out.println("Book updated.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    // remove book (only if no active issues)
    public void removeBook(int id) {
        Book b = findBook(id);

        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        for (Transaction t : transactions) {
            if (t.bookId == id && t.returnDay == -1) {
                System.out.println("Cannot remove. Book is currently issued.");
                return;
            }
        }

        books.remove(b);
        System.out.println("Book removed successfully.");
    }

    public void registerUser(String name) {
        User user = new User(userIdCounter++, name);
        users.add(user);

        System.out.println("User registered. ID: " + user.getUserId());
    }

    // show users and books count
    public void viewUsers() {
        System.out.println("\n--- Users List ---");

        for (User u : users) {
            System.out.print("User ID: " + u.getUserId() + " | Name: " + u.getName());

            HashMap<Integer, Integer> map = new HashMap<>();

            for (Transaction t : transactions) {
                if (t.userId == u.getUserId() && t.returnDay == -1) {
                    map.put(t.bookId, map.getOrDefault(t.bookId, 0) + 1);
                }
            }

            if (map.isEmpty()) {
                System.out.print(" | No books issued");
            } else {
                System.out.print(" | Books: ");
                boolean first = true;

                for (int bookId : map.keySet()) {
                    Book b = findBook(bookId);
                    if (b != null) {
                        if (!first) System.out.print(", ");
                        System.out.print(b.getTitle() + "(" + map.get(bookId) + ")");
                        first = false;
                    }
                }
            }
            System.out.println();
        }
    }

    public void issueBook(int bookId, int userId) {
        Book b = findBook(bookId);
        User u = findUser(userId);

        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        if (u == null) {
            System.out.println("Invalid user ID.");
            return;
        }

        if (b.getStock() == 0) {
            System.out.println("No copies available.");
            return;
        }

        b.decreaseStock();
        transactions.add(new Transaction(bookId, userId));

        System.out.println("Book issued.");
    }

    public void returnBook(int bookId, int userId, int daysTaken) {

        for (Transaction t : transactions) {
            if (t.bookId == bookId && t.userId == userId && t.returnDay == -1) {

                t.returnDay = daysTaken;

                Book b = findBook(bookId);
                if (b != null) b.increaseStock(1);

                int fine = (daysTaken > 7) ? (daysTaken - 7) * 10 : 0;

                System.out.println("Book returned.");
                System.out.println("Fine: " + fine);
                return;
            }
        }

        System.out.println("No matching record found.");
    }

    public void searchByTitlePrefix(String prefix) {
        boolean found = false;

        for (Book b : books) {
            if (b.getTitle().toLowerCase().startsWith(prefix.toLowerCase())) {
                System.out.println(b.getTitle() + " | Stock: " + b.getStock());
                found = true;
            }
        }

        if (!found) System.out.println("No books found.");
    }

    public void searchByAuthorPrefix(String prefix) {
        boolean found = false;

        for (Book b : books) {
            if (b.getAuthor().toLowerCase().startsWith(prefix.toLowerCase())) {
                System.out.println(b.getTitle() + " by " + b.getAuthor());
                found = true;
            }
        }

        if (!found) System.out.println("No books found.");
    }

    public void viewAvailableBooks() {
        System.out.println("\nAvailable Books:");

        boolean found = false;
        for (Book b : books) {
            if (b.getStock() > 0) {
                System.out.println("ID: " + b.getId() + " | Title: " + b.getTitle() +  " | Author: " + b.getAuthor() + " | Copies: " + b.getStock());
                found = true;
            }
        }

        if (!found) System.out.println("No books available.");
    }

    private Book findBook(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    private User findUser(int id) {
        for (User u : users) {
            if (u.getUserId() == id) return u;
        }
        return null;
    }

    public boolean hasBooks() {
        return !books.isEmpty();
    }
}