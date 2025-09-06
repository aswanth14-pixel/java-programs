class Vehicle {
    protected String make;
    protected String model;
    protected int year;
    protected double fuelLevel;

    public Vehicle(String make, String model, int year, double fuelLevel) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.fuelLevel = fuelLevel;
    }

    public void startVehicle() {
        System.out.println(make + " " + model + " started.");
    }

    public void stopVehicle() {
        System.out.println(make + " " + model + " stopped.");
    }

    public void refuel(double amount) {
        fuelLevel += amount;
        System.out.println(make + " " + model + " refueled by " + amount + " liters. Current fuel: " + fuelLevel);
    }

    public void displayVehicleInfo() {
        System.out.println("Vehicle Info:");
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Fuel Level: " + fuelLevel + " liters");
        System.out.println("----------------------");
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(String make, String model, int year, double fuelLevel, int doors) {
        super(make, model, year, fuelLevel);
        this.doors = doors;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Doors: " + doors);
        System.out.println("----------------------");
    }
}

class Truck extends Vehicle {
    private double loadCapacity;

    public Truck(String make, String model, int year, double fuelLevel, double loadCapacity) {
        super(make, model, year, fuelLevel);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Load Capacity: " + loadCapacity + " tons");
        System.out.println("----------------------");
    }
}

class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String make, String model, int year, double fuelLevel, boolean hasSidecar) {
        super(make, model, year, fuelLevel);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Has Sidecar: " + (hasSidecar ? "Yes" : "No"));
        System.out.println("----------------------");
    }
}

public class VehicleDemo {
    public static void main(String[] args) {
        Vehicle car = new Car("Toyota", "Corolla", 2020, 10.0, 4);
        Vehicle truck = new Truck("Ford", "F-150", 2018, 15.0, 3.5);
        Vehicle motorcycle = new Motorcycle("Harley-Davidson", "Sportster", 2019, 5.0, false);

        Vehicle[] vehicles = {car, truck, motorcycle};

        for (Vehicle v : vehicles) {
            v.startVehicle();
            v.displayVehicleInfo();
            v.refuel(5);
            v.stopVehicle();
            System.out.println();
        }
    }
}
