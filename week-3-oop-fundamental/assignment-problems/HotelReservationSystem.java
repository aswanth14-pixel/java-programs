import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class HotelReservationSystem {
    // Room Class
    static class Room {
        private String roomNumber;
        private String roomType;
        private double pricePerNight;
        private boolean isAvailable;
        private int maxOccupancy;

        public Room(String roomNumber, String roomType, double pricePerNight, int maxOccupancy) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.pricePerNight = pricePerNight;
            this.isAvailable = true;
            this.maxOccupancy = maxOccupancy;
        }

        public String getRoomNumber() { return roomNumber; }
        public String getRoomType() { return roomType; }
        public double getPricePerNight() { return pricePerNight; }
        public boolean isAvailable() { return isAvailable; }
        public int getMaxOccupancy() { return maxOccupancy; }
        public void setAvailable(boolean available) { this.isAvailable = available; }

        public String toString() {
            return "Room " + roomNumber + " (" + roomType + "), $" + pricePerNight + "/night, " +
                   (isAvailable ? "Available" : "Booked") + ", Max Occupancy: " + maxOccupancy;
        }
    }

    // Guest Class
    static class Guest {
        private String guestId;
        private String guestName;
        private String phoneNumber;
        private String email;
        private ArrayList<String> bookingHistory;

        public Guest(String guestId, String guestName, String phoneNumber, String email) {
            this.guestId = guestId;
            this.guestName = guestName;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.bookingHistory = new ArrayList<>();
        }

        public String getGuestId() { return guestId; }
        public String getGuestName() { return guestName; }
        public void addBookingToHistory(String bookingId) {
            bookingHistory.add(bookingId);
        }
    }

    // Booking Class
    static class Booking {
        private String bookingId;
        private Guest guest;
        private Room room;
        private String checkInDate;
        private String checkOutDate;
        private double totalAmount;

        public static int totalBookings = 0;
        public static double hotelRevenue = 0.0;
        public static String hotelName = "Grand Hotel";

        public Booking(String bookingId, Guest guest, Room room, String checkInDate, String checkOutDate) {
            this.bookingId = bookingId;
            this.guest = guest;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
            this.totalAmount = calculateBill();
            totalBookings++;
            hotelRevenue += totalAmount;
            room.setAvailable(false);
            guest.addBookingToHistory(bookingId);
        }

        public String getBookingId() { return bookingId; }
        public Guest getGuest() { return guest; }
        public Room getRoom() { return room; }

        public double calculateBill() {
            LocalDate in = LocalDate.parse(checkInDate);
            LocalDate out = LocalDate.parse(checkOutDate);
            long nights = ChronoUnit.DAYS.between(in, out);
            return nights * room.getPricePerNight();
        }

        public void cancelReservation() {
            room.setAvailable(true);
            hotelRevenue -= totalAmount;
            totalBookings--;
        }

        public static double getOccupancyRate(Room[] rooms) {
            int total = rooms.length, booked = 0;
            for (Room r : rooms) if (!r.isAvailable()) booked++;
            return (booked * 100.0) / total;
        }

        public static double getTotalRevenue() {
            return hotelRevenue;
        }

        public static String getMostPopularRoomType(Booking[] bookings) {
            Map<String, Integer> typeCount = new HashMap<>();
            for (Booking b : bookings) {
                if (b != null) {
                    String type = b.getRoom().getRoomType();
                    typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
                }
            }
            return typeCount.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse("N/A");
        }

        public String toString() {
            return "BookingID: " + bookingId + ", Guest: " + guest.getGuestName() + ", Room: " + room.getRoomNumber() +
                    ", Check-In: " + checkInDate + ", Check-Out: " + checkOutDate + ", Total: $" + totalAmount;
        }
    }

    // Main Program
    public static void main(String[] args) {
        Room[] rooms = {
            new Room("101", "Single", 100.0, 1),
            new Room("102", "Double", 150.0, 2),
            new Room("201", "Suite", 300.0, 4),
            new Room("202", "Single", 100.0, 1),
            new Room("203", "Double", 150.0, 2)
        };

        Booking[] bookings = new Booking[20];
        int bookingIndex = 0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Welcome to " + Booking.hotelName + " ---");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View Bookings");
            System.out.println("5. View Hotel Reports");
            System.out.println("6. Exit");
            System.out.print("Select option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    for (Room r : rooms) {
                        if (r.isAvailable()) System.out.println(r);
                    }
                    break;
                case 2:
                    System.out.print("Enter Guest ID: ");
                    String guestId = sc.nextLine();
                    System.out.print("Enter Guest Name: ");
                    String guestName = sc.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phone = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    Guest guest = new Guest(guestId, guestName, phone, email);

                    System.out.print("Enter Room Number to Book: ");
                    String roomNum = sc.nextLine();
                    Room roomToBook = null;
                    for (Room r : rooms) {
                        if (r.getRoomNumber().equals(roomNum)) {
                            roomToBook = r;
                            break;
                        }
                    }
                    if (roomToBook == null || !roomToBook.isAvailable()) {
                        System.out.println("Room not available.");
                        break;
                    }

                    System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
                    String checkIn = sc.nextLine();
                    System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
                    String checkOut = sc.nextLine();

                    String bookingId = "BKG" + (Booking.totalBookings + 1);
                    Booking booking = new Booking(bookingId, guest, roomToBook, checkIn, checkOut);
                    bookings[bookingIndex++] = booking;

                    System.out.println("Booking successful!\n" + booking);
                    break;
                case 3:
                    System.out.print("Enter Booking ID to cancel: ");
                    String cancelId = sc.nextLine();
                    boolean found = false;
                    for (int i = 0; i < bookingIndex; i++) {
                        if (bookings[i] != null && bookings[i].getBookingId().equals(cancelId)) {
                            bookings[i].cancelReservation();
                            bookings[i] = null;
                            System.out.println("Booking cancelled.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("Booking ID not found.");
                    break;
                case 4:
                    for (Booking b : bookings) {
                        if (b != null) System.out.println(b);
                    }
                    break;
                case 5:
                    System.out.printf("Occupancy Rate: %.2f%%\n", Booking.getOccupancyRate(rooms));
                    System.out.printf("Total Revenue: $%.2f\n", Booking.getTotalRevenue());
                    System.out.println("Most Popular Room Type: " + Booking.getMostPopularRoomType(bookings));
                    break;
                case 6:
                    System.out.println("Exiting. Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}