// keeps track of issue/return info
public class Transaction {
    int bookId;
    int userId;
    int issueDay;
    int dueDay;
    int returnDay;

    public Transaction(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.issueDay = 1;
        this.dueDay = 7;
        this.returnDay = -1; // as on return day, book is not returned yet
    }
}