class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;
    private static int totalBooks = 0;
    private static int availableBooks = 0;

    // Constructor
    public Book(String title, String author) {
        this.bookId = generateBookId();
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        totalBooks++;
        availableBooks++;
    }

    // Methods
    public void issueBook() {
        if (isAvailable) {
            isAvailable = false;
            availableBooks--;
            System.out.println("Book issued: " + title);
        } else {
            System.out.println("Book not available!");
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            availableBooks++;
            System.out.println("Book returned: " + title);
        }
    }

    public void displayBookInfo() {
        System.out.println("BookId: " + bookId + ", Title: " + title +
                ", Author: " + author + ", Available: " + isAvailable);
    }

    // Static
    private static String generateBookId() {
        return "B" + String.format("%03d", totalBooks + 1);
    }

    public static int getAvailableBooks() {
        return availableBooks;
    }

    public String getBookId() {
        return bookId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}

class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;

    private static int totalMembers = 0;

    public Member(String name) {
        this.memberId = generateMemberId();
        this.memberName = name;
        this.booksIssued = new String[5]; // max 5 books
        this.bookCount = 0;
        totalMembers++;
    }

    public void borrowBook(Book book) {
        if (book.isAvailable() && bookCount < booksIssued.length) {
            book.issueBook();
            booksIssued[bookCount++] = book.getBookId();
        } else {
            System.out.println("Cannot borrow book!");
        }
    }

    public void returnBook(String bookId, Book[] books) {
        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i].equals(bookId)) {
                for (Book b : books) {
                    if (b.getBookId().equals(bookId)) {
                        b.returnBook();
                        break;
                    }
                }
                booksIssued[i] = booksIssued[bookCount - 1];
                booksIssued[bookCount - 1] = null;
                bookCount--;
                return;
            }
        }
        System.out.println("Book not found in member's list!");
    }

    public void displayMemberInfo() {
        System.out.print("MemberId: " + memberId + ", Name: " + memberName + ", Books: ");
        for (int i = 0; i < bookCount; i++) {
            System.out.print(booksIssued[i] + " ");
        }
        System.out.println();
    }

    private static String generateMemberId() {
        return "M" + String.format("%03d", totalMembers + 1);
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Book[] books = new Book[3];
        books[0] = new Book("Java Basics", "James Gosling");
        books[1] = new Book("C++ Guide", "Bjarne Stroustrup");
        books[2] = new Book("Python Intro", "Guido van Rossum");

        Member[] members = new Member[2];
        members[0] = new Member("Alice");
        members[1] = new Member("Bob");

        members[0].borrowBook(books[0]);
        members[1].borrowBook(books[0]); // not available
        members[1].borrowBook(books[1]);

        members[0].displayMemberInfo();
        members[1].displayMemberInfo();

        members[0].returnBook("B001", books);
        members[1].borrowBook(books[0]);

        System.out.println("\nBooks Info:");
        for (Book b : books) {
            b.displayBookInfo();
        }
    }
}
