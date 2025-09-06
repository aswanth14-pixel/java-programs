import java.util.*;

public class EmployeeManagementSystem {

    static class Employee {
        String empId, empName, department, designation, joinDate, empType;
        double baseSalary;
        boolean[] attendanceRecord = new boolean[30];

        static int totalEmployees = 0;
        static String companyName = "TechNova Solutions";
        static double totalSalaryExpense = 0.0;
        static int workingDaysPerMonth = 22;

        public Employee(String empId, String empName, String department, String designation,
                        double baseSalary, String joinDate, String empType) {
            this.empId = empId;
            this.empName = empName;
            this.department = department;
            this.designation = designation;
            this.baseSalary = baseSalary;
            this.joinDate = joinDate;
            this.empType = empType;
            totalEmployees++;
        }

        public void markAttendance(int day, boolean present) {
            if (day < 1 || day > 30) {
                System.out.println("Invalid day: " + day);
                return;
            }
            attendanceRecord[day - 1] = present;
        }

        public int getDaysPresent() {
            int count = 0;
            for (boolean present : attendanceRecord) {
                if (present) count++;
            }
            return count;
        }

        public double calculateSalary() {
            int daysPresent = getDaysPresent();
            double dailyRate = baseSalary / workingDaysPerMonth;

            double salary = 0;
            switch (empType.toLowerCase()) {
                case "full-time" -> salary = baseSalary + calculateBonus();
                case "part-time" -> salary = dailyRate * daysPresent;
                case "contract" -> salary = dailyRate * daysPresent * 0.9; // 10% tax
            }

            totalSalaryExpense += salary;
            return salary;
        }

        public double calculateBonus() {
            if (empType.equalsIgnoreCase("full-time")) {
                int daysPresent = getDaysPresent();
                if (daysPresent >= workingDaysPerMonth) return baseSalary * 0.10;
                else if (daysPresent >= workingDaysPerMonth - 2) return baseSalary * 0.05;
            }
            return 0;
        }

        public void generatePaySlip() {
            double salary = calculateSalary();
            double bonus = calculateBonus();
            System.out.printf("\n--- Pay Slip for %s (%s) ---\n", empName, empId);
            System.out.printf("Base Salary: $%.2f\n", baseSalary);
            System.out.printf("Days Present: %d\n", getDaysPresent());
            System.out.printf("Bonus: $%.2f\n", bonus);
            System.out.printf("Total Salary: $%.2f\n", salary);
        }

        public void requestLeave(int day) {
            if (day < 1 || day > 30) {
                System.out.println("Invalid day for leave.");
                return;
            }
            attendanceRecord[day - 1] = false;
            System.out.printf("%s has requested leave on day %d\n", empName, day);
        }
    }

    static class Department {
        String deptId, deptName;
        Employee manager;
        List<Employee> employees = new ArrayList<>();
        double budget;

        public Department(String deptId, String deptName, Employee manager, double budget) {
            this.deptId = deptId;
            this.deptName = deptName;
            this.manager = manager;
            this.budget = budget;
        }

        public void addEmployee(Employee e) {
            employees.add(e);
        }

        public double calculateDepartmentExpense() {
            double expense = 0;
            for (Employee e : employees) {
                expense += e.calculateSalary();
            }
            return expense;
        }

        public void printDepartmentReport() {
            System.out.printf("\n--- Department: %s ---\n", deptName);
            System.out.println("Manager: " + manager.empName);
            System.out.println("Employees:");
            for (Employee e : employees) {
                System.out.printf("  - %s (%s)\n", e.empName, e.designation);
            }
        }
    }

    public static double calculateCompanyPayroll(List<Employee> employees) {
        double total = 0;
        for (Employee e : employees) {
            total += e.calculateSalary();
        }
        return total;
    }

    public static void getDepartmentWiseExpenses(List<Department> departments) {
        System.out.println("\n--- Department-wise Salary Expenses ---");
        for (Department d : departments) {
            double exp = d.calculateDepartmentExpense();
            System.out.printf("Department: %s | Expense: $%.2f\n", d.deptName, exp);
        }
    }

    public static void getAttendanceReport(List<Employee> employees) {
        System.out.println("\n--- Attendance Report ---");
        for (Employee e : employees) {
            int present = e.getDaysPresent();
            System.out.printf("%s (%s) - Present: %d days\n", e.empName, e.empId, present);
        }
    }

    public static void main(String[] args) {
        List<Employee> allEmployees = new ArrayList<>();
        List<Department> allDepartments = new ArrayList<>();

        Employee mgrIT = new Employee("E100", "Alice", "IT", "Manager", 7000, "2020-01-01", "Full-Time");
        Employee emp1 = new Employee("E101", "Bob", "IT", "Developer", 5000, "2021-05-10", "Full-Time");
        Employee emp2 = new Employee("E102", "Charlie", "IT", "Tester", 4000, "2022-03-12", "Part-Time");
        Employee emp3 = new Employee("E103", "Diana", "HR", "Recruiter", 4500, "2023-07-01", "Full-Time");
        Employee mgrHR = new Employee("E104", "Eva", "HR", "Manager", 6500, "2019-11-05", "Full-Time");
        Employee emp4 = new Employee("E105", "Frank", "HR", "Intern", 2000, "2024-02-01", "Contract");

        allEmployees.addAll(Arrays.asList(mgrIT, emp1, emp2, emp3, mgrHR, emp4));

        Department itDept = new Department("D01", "IT", mgrIT, 20000);
        itDept.addEmployee(mgrIT);
        itDept.addEmployee(emp1);
        itDept.addEmployee(emp2);

        Department hrDept = new Department("D02", "HR", mgrHR, 15000);
        hrDept.addEmployee(mgrHR);
        hrDept.addEmployee(emp3);
        hrDept.addEmployee(emp4);

        allDepartments.add(itDept);
        allDepartments.add(hrDept);

        // Simulate attendance
        for (int day = 1; day <= 22; day++) {
            emp1.markAttendance(day, true);
            emp2.markAttendance(day, day % 2 == 0);
            emp3.markAttendance(day, day % 3 != 0);
            emp4.markAttendance(day, day % 4 != 0);
            mgrIT.markAttendance(day, true);
            mgrHR.markAttendance(day, true);
        }

        // Leaves
        emp1.requestLeave(23);
        emp3.requestLeave(24);

        // Generate payslips
        for (Employee e : allEmployees) {
            e.generatePaySlip();
        }

        // Reports
        getAttendanceReport(allEmployees);
        getDepartmentWiseExpenses(allDepartments);

        double totalPayroll = calculateCompanyPayroll(allEmployees);
        System.out.printf("\nTotal Company Payroll: $%.2f\n", totalPayroll);
    }
}