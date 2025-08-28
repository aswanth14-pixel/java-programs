class Employee {
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;
    private static int totalEmployees = 0;

    // Constructors
    public Employee(String name, String dept, double salary) { // Full-time
        this.empId = generateEmpId();
        this.empName = name;
        this.department = dept;
        this.baseSalary = salary;
        this.empType = "Full-time";
        totalEmployees++;
    }

    public Employee(String name, String dept, double hourlyRate, int hours) { // Part-time
        this.empId = generateEmpId();
        this.empName = name;
        this.department = dept;
        this.baseSalary = hourlyRate * hours;
        this.empType = "Part-time";
        totalEmployees++;
    }

    public Employee(String name, double fixedAmount) { // Contract
        this.empId = generateEmpId();
        this.empName = name;
        this.department = "Contract";
        this.baseSalary = fixedAmount;
        this.empType = "Contract";
        totalEmployees++;
    }

    // Overloaded Salary
    public double calculateSalary() {
        if (empType.equals("Full-time")) {
            return baseSalary + 2000; // bonus
        } else if (empType.equals("Part-time")) {
            return baseSalary;
        } else {
            return baseSalary;
        }
    }

    // Overloaded Tax
    public double calculateTax() {
        if (empType.equals("Full-time")) return calculateSalary() * 0.1;
        else if (empType.equals("Part-time")) return calculateSalary() * 0.05;
        else return calculateSalary() * 0.02;
    }

    public void generatePaySlip() {
        System.out.println("PaySlip: " + empId + " | " + empName +
                " | " + empType +
                " | Salary: " + calculateSalary() +
                " | Tax: " + calculateTax());
    }

    public void displayEmployeeInfo() {
        System.out.println(empId + " | " + empName + " | " + department + " | " + empType);
    }

    private static String generateEmpId() {
        return "E" + String.format("%03d", totalEmployees + 1);
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        Employee e1 = new Employee("Alice", "IT", 50000); // full-time
        Employee e2 = new Employee("Bob", "Sales", 200, 20); // part-time
        Employee e3 = new Employee("Charlie", 30000); // contract

        e1.generatePaySlip();
        e2.generatePaySlip();
        e3.generatePaySlip();
    }
}
