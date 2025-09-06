import java.util.*;

public class FleetManagementSystem {

    static abstract class Vehicle {
        String vehicleId, brand, model, fuelType, currentStatus;
        int year;
        double mileage;
        Driver assignedDriver;

        static int totalVehicles = 0;
        static double fleetValue = 0.0;
        static String companyName = "TransLogix Corp";
        static double totalFuelConsumption = 0.0;

        public Vehicle(String vehicleId, String brand, String model, int year, double mileage, String fuelType) {
            this.vehicleId = vehicleId;
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.mileage = mileage;
            this.fuelType = fuelType;
            this.currentStatus = "Available";
            totalVehicles++;
        }

        public void assignDriver(Driver d) {
            this.assignedDriver = d;
            d.assignedVehicle = this;
            currentStatus = "Assigned";
        }

        public void scheduleMaintenance() {
            currentStatus = "Under Maintenance";
            System.out.println("Maintenance scheduled for " + vehicleId);
        }

        public abstract double calculateRunningCost();

        public void updateMileage(double km) {
            mileage += km;
            totalFuelConsumption += km / getFuelEfficiency();
        }

        public boolean checkServiceDue() {
            return mileage % 10000 < 500;
        }

        public abstract double getFuelEfficiency();

        public String toString() {
            return String.format("%s %s [%s] (%s) - Mileage: %.1f km", brand, model, vehicleId, currentStatus, mileage);
        }
    }

    static class Car extends Vehicle {
        boolean luxury;

        public Car(String id, String brand, String model, int year, double mileage, String fuelType, boolean luxury) {
            super(id, brand, model, year, mileage, fuelType);
            this.luxury = luxury;
        }

        public double calculateRunningCost() {
            return mileage * (luxury ? 0.15 : 0.10);
        }

        public double getFuelEfficiency() {
            return luxury ? 10 : 15;
        }
    }

    static class Bus extends Vehicle {
        int seatingCapacity;

        public Bus(String id, String brand, String model, int year, double mileage, String fuelType, int seats) {
            super(id, brand, model, year, mileage, fuelType);
            this.seatingCapacity = seats;
        }

        public double calculateRunningCost() {
            return mileage * 0.2;
        }

        public double getFuelEfficiency() {
            return 6;
        }
    }

    static class Truck extends Vehicle {
        double loadCapacity;

        public Truck(String id, String brand, String model, int year, double mileage, String fuelType, double load) {
            super(id, brand, model, year, mileage, fuelType);
            this.loadCapacity = load;
        }

        public double calculateRunningCost() {
            return mileage * 0.25;
        }

        public double getFuelEfficiency() {
            return 5;
        }
    }

    static class Driver {
        String driverId, driverName, licenseType;
        Vehicle assignedVehicle;
        int totalTrips = 0;

        public Driver(String id, String name, String licenseType) {
            this.driverId = id;
            this.driverName = name;
            this.licenseType = licenseType;
        }

        public void completeTrip(double km) {
            if (assignedVehicle == null) {
                System.out.println(driverName + " has no assigned vehicle.");
                return;
            }
            assignedVehicle.updateMileage(km);
            totalTrips++;
            System.out.printf("%s completed a trip of %.1f km with %s\n", driverName, km, assignedVehicle.vehicleId);
        }

        public String toString() {
            return String.format("Driver: %s (%s) - Trips: %d", driverName, driverId, totalTrips);
        }
    }

    static class FleetManager {
        List<Vehicle> vehicles = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();

        public void addVehicle(Vehicle v) {
            vehicles.add(v);
            Vehicle.fleetValue += v.calculateRunningCost() * 0.05; // Estimated fleet value increase
        }

        public void addDriver(Driver d) {
            drivers.add(d);
        }

        public static void getFleetUtilization(List<Vehicle> vehicles) {
            long active = vehicles.stream().filter(v -> v.currentStatus.equals("Assigned")).count();
            System.out.printf("Fleet Utilization: %.2f%%\n", (active * 100.0 / vehicles.size()));
        }

        public static double calculateTotalMaintenanceCost(List<Vehicle> vehicles) {
            double cost = 0;
            for (Vehicle v : vehicles) {
                if (v.checkServiceDue()) {
                    cost += 200 + (v.mileage * 0.01);
                }
            }
            return cost;
        }

        public static void getVehiclesByType(List<Vehicle> vehicles, String type) {
            System.out.println("\n--- Vehicles of type: " + type + " ---");
            for (Vehicle v : vehicles) {
                if (v.getClass().getSimpleName().equalsIgnoreCase(type)) {
                    System.out.println(v);
                }
            }
        }
    }

    public static void main(String[] args) {
        FleetManager fm = new FleetManager();

        // Create Vehicles
        Car car1 = new Car("V101", "Toyota", "Camry", 2020, 45000, "Petrol", false);
        Car car2 = new Car("V102", "Mercedes", "S-Class", 2021, 30000, "Diesel", true);
        Bus bus1 = new Bus("V201", "Volvo", "B9R", 2019, 80000, "Diesel", 50);
        Truck truck1 = new Truck("V301", "Tata", "LPT", 2018, 120000, "Diesel", 12);

        fm.addVehicle(car1);
        fm.addVehicle(car2);
        fm.addVehicle(bus1);
        fm.addVehicle(truck1);

        // Create Drivers
        Driver d1 = new Driver("D001", "John", "Car");
        Driver d2 = new Driver("D002", "Alice", "Bus");
        Driver d3 = new Driver("D003", "Mike", "Truck");

        fm.addDriver(d1);
        fm.addDriver(d2);
        fm.addDriver(d3);

        // Assign drivers
        car1.assignDriver(d1);
        bus1.assignDriver(d2);
        truck1.assignDriver(d3);

        // Simulate trips
        d1.completeTrip(150);
        d2.completeTrip(300);
        d3.completeTrip(500);

        // Schedule maintenance
        car2.scheduleMaintenance();

        // Reports
        System.out.println("\n=== Vehicle List ===");
        for (Vehicle v : fm.vehicles) {
            System.out.println(v);
        }

        System.out.println("\n=== Driver List ===");
        for (Driver d : fm.drivers) {
            System.out.println(d);
        }

        FleetManager.getFleetUtilization(fm.vehicles);

        double maintenanceCost = FleetManager.calculateTotalMaintenanceCost(fm.vehicles);
        System.out.printf("Total Maintenance Cost Estimate: $%.2f\n", maintenanceCost);

        FleetManager.getVehiclesByType(fm.vehicles, "Car");
        FleetManager.getVehiclesByType(fm.vehicles, "Bus");
        FleetManager.getVehiclesByType(fm.vehicles, "Truck");

        System.out.printf("Total Fuel Consumption: %.2f liters\n", Vehicle.totalFuelConsumption);
        System.out.printf("Total Fleet Value: $%.2f\n", Vehicle.fleetValue);
    }
}