import java.util.Scanner;

public class Main {

    // to safely read integer input
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // for reading non-empty string
    private static String readString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        System.out.println("\n====== LIBRARY MANAGEMENT SYSTEM ======");

        while (true) {
            System.out.println("\n-------------------------------");
            System.out.println("1. Add Book  2. Remove Book  3. Update Book  4. Issue Book  5. Return Book");
            System.out.println("6. Search Book  7. View Books  8. Register User  9. View Users  10. Exit");

            int ch = readInt(sc, "Enter your choice: ");

            switch (ch) {

                case 1:
                    System.out.println("\n--- Add Book ---");

                    String title = readString(sc, "Enter Book Title: ");
                    String author = readString(sc, "Enter Author Name: ");
                    int count = readInt(sc, "Enter Number of Copies: ");

                    if (count <= 0) {
                        System.out.println("Invalid number of copies.");
                        break;
                    }

                    lib.addBook(title, author, count);
                    break;

                case 2:
                    System.out.println("\n--- Remove Book ---");

                    int removeId = readInt(sc, "Enter Book ID to remove: ");
                    lib.removeBook(removeId);
                    break;

                case 3:
                    System.out.println("\n--- Update Book ---");

                    if (!lib.hasBooks()) {
                        System.out.println("No books available in library.");
                        break;
                    }

                    int id = readInt(sc, "Enter Book ID to update: ");
                    String nt = readString(sc, "Enter new Title: ");
                    String na = readString(sc, "Enter new Author: ");

                    lib.updateBook(id, nt, na);
                    break;

                case 4:
                    System.out.println("\n--- Issue Book ---");

                    if (!lib.hasBooks()) {
                        System.out.println("No books available in library.");
                        break;
                    }

                    int bid = readInt(sc, "Enter Book ID: ");
                    int uid = readInt(sc, "Enter User ID: ");

                    lib.issueBook(bid, uid);
                    break;

                case 5:
                    System.out.println("\n--- Return Book ---");

                    int rid = readInt(sc, "Enter Book ID: ");
                    int ruid = readInt(sc, "Enter User ID: ");
                    int days = readInt(sc, "Enter number of days taken: ");

                    if (days <= 0) {
                        System.out.println("Invalid number of days.");
                        break;
                    }

                    lib.returnBook(rid, ruid, days);
                    break;

                case 6:
                    System.out.println("\n--- Search Book ---");

                    if (!lib.hasBooks()) {
                        System.out.println("No books available in library.");
                        break;
                    }

                    System.out.println("1. Search by Title");
                    System.out.println("2. Search by Author");

                    int opt = readInt(sc, "Choose option: ");
                    String prefix = readString(sc, "Enter prefix: ");

                    if (opt == 1)
                        lib.searchByTitlePrefix(prefix);
                    else if (opt == 2)
                        lib.searchByAuthorPrefix(prefix);
                    else
                        System.out.println("Invalid option.");

                    break;

                case 7:
                    lib.viewAvailableBooks();
                    break;

                case 8:
                    System.out.println("\n--- Register User ---");

                    String name = readString(sc, "Enter User Name: ");
                    lib.registerUser(name);
                    break;

                case 9:
                    lib.viewUsers();
                    break;

                case 10:
                    System.out.println("Exiting system...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}