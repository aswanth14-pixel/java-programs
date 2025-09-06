public class Car {
    private String brand;
    private String model;
    private int year;
    private String color;
    private boolean isRunning;

    public Car(String brand, String model, int year, String color) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.isRunning = false;
    }

    public void startEngine() {
        if (!isRunning) {
            isRunning = true;
            System.out.println(brand + " " + model + " engine started.");
        } else {
            System.out.println(brand + " " + model + " engine is already running.");
        }
    }

    public void stopEngine() {
        if (isRunning) {
            isRunning = false;
            System.out.println(brand + " " + model + " engine stopped.");
        } else {
            System.out.println(brand + " " + model + " engine is already stopped.");
        }
    }

    public void displayInfo() {
        System.out.println("Car Information:");
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Color: " + color);
        System.out.println("Engine Running: " + (isRunning ? "Yes" : "No"));
        System.out.println("------------------------");
    }

    public int getAge(int currentYear) {
        return currentYear - year;
    }

    public static void main(String[] args) {
        Car car1 = new Car("Toyota", "Camry", 2015, "Red");
        Car car2 = new Car("Honda", "Civic", 2018, "Blue");
        Car car3 = new Car("Ford", "Mustang", 2020, "Black");

        car1.displayInfo();
        car1.startEngine();
        car1.displayInfo();
        System.out.println(car1.brand + " " + car1.model + " age: " + car1.getAge(2025) + " years");
        car1.stopEngine();

        System.out.println();

        car2.displayInfo();
        car2.startEngine();
        car2.stopEngine();
        System.out.println(car2.brand + " " + car2.model + " age: " + car2.getAge(2025) + " years");

        System.out.println();

        car3.displayInfo();
        car3.startEngine();
        System.out.println(car3.brand + " " + car3.model + " age: " + car3.getAge(2025) + " years");
        car3.stopEngine();
    }
}
