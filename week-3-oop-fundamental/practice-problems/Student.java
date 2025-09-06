public class Student {
    private String studentId;
    private String name;
    private double grade;
    private String course;

    // Default constructor
    public Student() {
        this.studentId = "";
        this.name = "";
        this.grade = 0.0;
        this.course = "";
    }

    // Parameterized constructor
    public Student(String studentId, String name, double grade, String course) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
        this.course = course;
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // Method to calculate letter grade
    public char calculateLetterGrade() {
        if (grade >= 90) {
            return 'A';
        } else if (grade >= 80) {
            return 'B';
        } else if (grade >= 70) {
            return 'C';
        } else if (grade >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    // Method to display student info
    public void displayStudent() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Grade: " + grade);
        System.out.println("Letter Grade: " + calculateLetterGrade());
        System.out.println("----------------------------");
    }

    public static void main(String[] args) {
        // Create a student using default constructor
        Student student1 = new Student();
        student1.setStudentId("S1001");
        student1.setName("Alice Johnson");
        student1.setGrade(85.5);
        student1.setCourse("Mathematics");

        // Create another student using parameterized constructor
        Student student2 = new Student("S1002", "Bob Smith", 92.3, "Physics");

        // Demonstrate getters/setters
        System.out.println(student1.getName() + " is enrolled in " + student1.getCourse());
        student1.setCourse("Advanced Mathematics");
        System.out.println(student1.getName() + " updated course to " + student1.getCourse());

        // Display both students' info and letter grades
        student1.displayStudent();
        student2.displayStudent();
    }
}
