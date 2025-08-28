class Vehicle {
    private String vehicleId;
    private String brand;
    private String model;
    private double rentPerDay;
    private boolean isAvailable;

    private static int totalVehicles = 0;
    private static double totalRevenue = 0;
    private static String companyName = "Default Rentals";
    private static int rentalDays = 0;

    public Vehicle(String brand, String model, double rent) {
        this.vehicleId = generateVehicleId();
        this.brand = brand;
        this.model = model;
        this.rentPerDay = rent;
        this.isAvailable = true;
        totalVehicles++;
    }

    public double rentVehicle(int days) {
        if (isAvailable) {
            isAvailable = false;
            double rent = calculateRent(days);
            rentalDays += days;
            return rent;
        } else {
            System.out.println("Vehicle not available!");
            return 0;
        }
    }

    public void returnVehicle() {
        isAvailable = true;
        System.out.println("Vehicle returned: " + vehicleId);
    }

    public double calculateRent(int days) {
        double rent = rentPerDay * days;
        totalRevenue += rent;
        return rent;
    }

    public void displayVehicleInfo() {
        System.out.println("VehicleId: " + vehicleId +
                ", " + brand + " " + model +
                ", Rent/day: " + rentPerDay +
                ", Available: " + isAvailable);
    }

    // Static methods
    private static String generateVehicleId() {
        return "V" + String.format("%03d", totalVehicles + 1);
    }

    public static void setCompanyName(String name) {
        companyName = name;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public static double getAverageRentPerDay() {
        return rentalDays > 0 ? totalRevenue / rentalDays : 0;
    }

    public static void displayCompanyStats() {
        System.out.println("Company: " + companyName +
                ", Total Vehicles: " + totalVehicles +
                ", Total Revenue: " + totalRevenue +
                ", Avg Rent/Day: " + getAverageRentPerDay());
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Vehicle.setCompanyName("SuperCars Rental");

        Vehicle v1 = new Vehicle("Toyota", "Innova", 1500);
        Vehicle v2 = new Vehicle("Honda", "City", 1200);

        v1.displayVehicleInfo();
        v2.displayVehicleInfo();

        double rent1 = v1.rentVehicle(5);
        System.out.println("Rent paid: " + rent1);
        v1.returnVehicle();

        double rent2 = v2.rentVehicle(3);
        System.out.println("Rent paid: " + rent2);

        Vehicle.displayCompanyStats();
    }
}
