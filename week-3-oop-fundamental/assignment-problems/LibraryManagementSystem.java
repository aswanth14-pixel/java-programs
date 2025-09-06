import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class LibraryManagementSystem {
    // Book Class
    static class Book {
        String bookId, title, author, isbn, category;
        boolean isIssued = false;
        String issueDate;
        String dueDate;
        int issueCount = 0;

        public Book(String bookId, String title, String author, String isbn, String category) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.category = category;
        }

        public boolean isOverdue() {
            if (dueDate == null) return false;
            LocalDate due = LocalDate.parse(dueDate);
            return LocalDate.now().isAfter(due);
        }

        public long daysOverdue() {
            if (!isOverdue()) return 0;
            LocalDate due = LocalDate.parse(dueDate);
            return ChronoUnit.DAYS.between(due, LocalDate.now());
        }

        public String toString() {
            return String.format("Book[%s] %s by %s (%s) | %s", bookId, title, author, category, (isIssued ? "Issued" : "Available"));
        }
    }

    // Member Class
    static class Member {
        String memberId, memberName, memberType, membershipDate;
        List<Book> booksIssued = new ArrayList<>();
        double totalFines = 0;

        public static int totalMembers = 0;
        public static double finePerDay = 2.0;
        public static int maxBooksAllowed = 3;
        public static String libraryName = "City Public Library";

        public Member(String memberId, String memberName, String memberType, String membershipDate) {
            this.memberId = memberId;
            this.memberName = memberName;
            this.memberType = memberType;
            this.membershipDate = membershipDate;
            totalMembers++;
        }

        public int getMaxAllowedBooks() {
            return switch (memberType.toLowerCase()) {
                case "student" -> 3;
                case "faculty" -> 5;
                default -> 2;
            };
        }

        public int getBorrowDays() {
            return switch (memberType.toLowerCase()) {
                case "student" -> 14;
                case "faculty" -> 30;
                default -> 7;
            };
        }

        public void issueBook(Book book) {
            if (booksIssued.size() >= getMaxAllowedBooks()) {
                System.out.println("Book issue limit reached for " + memberType);
                return;
            }
            if (book.isIssued) {
                System.out.println("Book already issued.");
                return;
            }
            book.isIssued = true;
            book.issueDate = LocalDate.now().toString();
            book.dueDate = LocalDate.now().plusDays(getBorrowDays()).toString();
            booksIssued.add(book);
            book.issueCount++;
            System.out.println("Book issued successfully.");
        }

        public void returnBook(Book book) {
            if (!booksIssued.contains(book)) {
                System.out.println("Book not found in issued list.");
                return;
            }
            double fine = book.daysOverdue() * finePerDay;
            totalFines += fine;
            booksIssued.remove(book);
            book.isIssued = false;
            book.issueDate = null;
            book.dueDate = null;
            System.out.printf("Book returned. Fine: $%.2f\n", fine);
        }

        public void renewBook(Book book) {
            if (!booksIssued.contains(book)) {
                System.out.println("Book not found.");
                return;
            }
            double fine = book.daysOverdue() * finePerDay;
            totalFines += fine;
            book.dueDate = LocalDate.now().plusDays(getBorrowDays()).toString();
            System.out.printf("Book renewed. Fine: $%.2f\n", fine);
        }

        public void calculateFine() {
            double total = 0;
            for (Book b : booksIssued) {
                total += b.daysOverdue() * finePerDay;
            }
            System.out.printf("Outstanding fine: $%.2f\n", total);
        }

        public String toString() {
            return String.format("Member[%s] %s (%s) | Books issued: %d | Fines: $%.2f", memberId, memberName, memberType, booksIssued.size(), totalFines);
        }
    }

    // Library Class
    static class Library {
        List<Book> books = new ArrayList<>();
        List<Member> members = new ArrayList<>();
        static int totalBooks = 0;

        public void addBook(Book b) {
            books.add(b);
            totalBooks++;
        }

        public void addMember(Member m) {
            members.add(m);
        }

        public void searchBooks(String keyword) {
            for (Book b : books) {
                if (b.title.toLowerCase().contains(keyword.toLowerCase()) || b.author.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println(b);
                }
            }
        }

        public void reserveBook(Member member, Book book) {
            if (!book.isIssued) {
                member.issueBook(book);
            } else {
                System.out.println("Book currently issued. Reservation feature not implemented.");
            }
        }

        public static void generateLibraryReport(List<Book> books, List<Member> members) {
            System.out.println("\n--- Library Report ---");
            System.out.println("Total Books: " + books.size());
            System.out.println("Total Members: " + members.size());
            long totalIssued = books.stream().filter(b -> b.isIssued).count();
            System.out.println("Books Currently Issued: " + totalIssued);
        }

        public static void getOverdueBooks(List<Book> books) {
            System.out.println("\n--- Overdue Books ---");
            for (Book b : books) {
                if (b.isOverdue()) {
                    System.out.printf("%s | Overdue by %d days\n", b, b.daysOverdue());
                }
            }
        }

        public static void getMostPopularBooks(List<Book> books) {
            books.sort((b1, b2) -> Integer.compare(b2.issueCount, b1.issueCount));
            System.out.println("\n--- Most Popular Books ---");
            for (int i = 0; i < Math.min(5, books.size()); i++) {
                Book b = books.get(i);
                System.out.printf("%s | Issued %d times\n", b.title, b.issueCount);
            }
        }
    }

    public static void main(String[] args) {
        Library lib = new Library();

        // Sample books
        Book b1 = new Book("B001", "Java Programming", "James Gosling", "12345", "Programming");
        Book b2 = new Book("B002", "Data Structures", "Mark Allen", "67890", "Computer Science");
        Book b3 = new Book("B003", "Operating Systems", "Abraham Silberschatz", "11223", "Technology");
        Book b4 = new Book("B004", "Discrete Math", "Rosen", "33445", "Mathematics");

        lib.addBook(b1);
        lib.addBook(b2);
        lib.addBook(b3);
        lib.addBook(b4);

        // Sample members
        Member m1 = new Member("M001", "Alice", "Student", "2022-01-15");
        Member m2 = new Member("M002", "Bob", "Faculty", "2021-09-20");

        lib.addMember(m1);
        lib.addMember(m2);

        // Operations
        m1.issueBook(b1);
        m1.issueBook(b2);

        m2.issueBook(b3);
        m2.issueBook(b4);

        // Simulate overdue
        b1.dueDate = LocalDate.now().minusDays(5).toString();
        b3.dueDate = LocalDate.now().minusDays(2).toString();

        m1.returnBook(b1);
        m2.renewBook(b3);

        lib.searchBooks("Java");

        Library.getOverdueBooks(lib.books);
        Library.getMostPopularBooks(lib.books);
        Library.generateLibraryReport(lib.books, lib.members);
    }
}