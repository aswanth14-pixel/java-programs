import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class StudentGradeManagementSystem {
    // Subject Class
    static class Subject {
        String subjectCode;
        String subjectName;
        int credits;
        String instructor;

        public Subject(String subjectCode, String subjectName, int credits, String instructor) {
            this.subjectCode = subjectCode;
            this.subjectName = subjectName;
            this.credits = credits;
            this.instructor = instructor;
        }
    }

    // Student Class
    static class Student {
        String studentId;
        String studentName;
        String className;
        String[] subjects;
        double[][] marks; // [subject][assessments]
        double gpa;

        static int totalStudents = 0;
        static String schoolName = "Bright Future School";
        static String[] gradingScale = {"A", "B", "C", "D", "F"};
        static double passPercentage = 40.0;

        public Student(String studentId, String studentName, String className, String[] subjects) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.className = className;
            this.subjects = subjects;
            this.marks = new double[subjects.length][3]; // 3 assessments per subject
            this.gpa = 0.0;
            totalStudents++;
        }

        public void addMarks(String subject, double[] subjectMarks) {
            for (int i = 0; i < subjects.length; i++) {
                if (subjects[i].equalsIgnoreCase(subject)) {
                    marks[i] = subjectMarks;
                    return;
                }
            }
            System.out.println("Subject not found: " + subject);
        }

        public void calculateGPA() {
            double total = 0;
            int count = 0;
            for (int i = 0; i < marks.length; i++) {
                double subjectTotal = 0;
                for (int j = 0; j < marks[i].length; j++) {
                    subjectTotal += marks[i][j];
                }
                double avg = subjectTotal / marks[i].length;
                total += avg;
                count++;
            }
            gpa = total / count;
        }

        public void generateReportCard() {
            System.out.println("\nReport Card for " + studentName + " (ID: " + studentId + ")");
            System.out.println("Class: " + className);
            for (int i = 0; i < subjects.length; i++) {
                double subjectTotal = 0;
                for (double score : marks[i]) {
                    subjectTotal += score;
                }
                double avg = subjectTotal / marks[i].length;
                String grade = getGrade(avg);
                System.out.printf("Subject: %-15s | Avg: %.2f | Grade: %s\n", subjects[i], avg, grade);
            }
            System.out.printf("Overall GPA: %.2f\n", gpa);
            System.out.println("Promotion Status: " + (checkPromotionEligibility() ? "Promoted" : "Not Promoted"));
        }

        public boolean checkPromotionEligibility() {
            for (int i = 0; i < marks.length; i++) {
                double subjectTotal = 0;
                for (double score : marks[i]) {
                    subjectTotal += score;
                }
                double avg = subjectTotal / marks[i].length;
                if (avg < passPercentage) return false;
            }
            return true;
        }

        private String getGrade(double score) {
            if (score >= 85) return "A";
            else if (score >= 70) return "B";
            else if (score >= 55) return "C";
            else if (score >= 40) return "D";
            else return "F";
        }

        public static void setGradingScale(String[] scale) {
            gradingScale = scale;
        }

        public static double calculateClassAverage(Student[] students) {
            double total = 0;
            int count = 0;
            for (Student s : students) {
                total += s.gpa;
                count++;
            }
            return count == 0 ? 0 : total / count;
        }

        public static void getTopPerformers(Student[] students, int count) {
            Arrays.sort(students, (a, b) -> Double.compare(b.gpa, a.gpa));
            System.out.println("\nTop " + count + " Performers:");
            for (int i = 0; i < count && i < students.length; i++) {
                System.out.printf("%d. %s (GPA: %.2f)\n", i + 1, students[i].studentName, students[i].gpa);
            }
        }

        public static void generateSchoolReport(Student[] students) {
            Map<String, List<Student>> classMap = new HashMap<>();
            for (Student s : students) {
                classMap.computeIfAbsent(s.className, k -> new ArrayList<>()).add(s);
            }

            System.out.println("\n--- School Report Summary ---");
            for (String cls : classMap.keySet()) {
                List<Student> classList = classMap.get(cls);
                Student[] classArray = classList.toArray(new Student[0]);
                double avg = calculateClassAverage(classArray);
                System.out.printf("Class: %s | Average GPA: %.2f\n", cls, avg);
            }
        }
    }

    public static void main(String[] args) {
        String[] commonSubjects = {"Math", "Science", "English"};
        Student[] students = new Student[5];

        students[0] = new Student("S001", "Alice", "Grade 10", commonSubjects);
        students[1] = new Student("S002", "Bob", "Grade 10", commonSubjects);
        students[2] = new Student("S003", "Charlie", "Grade 11", commonSubjects);
        students[3] = new Student("S004", "Diana", "Grade 11", commonSubjects);
        students[4] = new Student("S005", "Ethan", "Grade 10", commonSubjects);

        students[0].addMarks("Math", new double[]{90, 85, 88});
        students[0].addMarks("Science", new double[]{92, 80, 85});
        students[0].addMarks("English", new double[]{88, 90, 84});
        students[0].calculateGPA();

        students[1].addMarks("Math", new double[]{70, 65, 60});
        students[1].addMarks("Science", new double[]{75, 68, 72});
        students[1].addMarks("English", new double[]{78, 70, 74});
        students[1].calculateGPA();

        students[2].addMarks("Math", new double[]{88, 92, 90});
        students[2].addMarks("Science", new double[]{85, 87, 89});
        students[2].addMarks("English", new double[]{90, 91, 92});
        students[2].calculateGPA();

        students[3].addMarks("Math", new double[]{55, 50, 58});
        students[3].addMarks("Science", new double[]{60, 62, 59});
        students[3].addMarks("English", new double[]{65, 68, 63});
        students[3].calculateGPA();

        students[4].addMarks("Math", new double[]{35, 38, 30});
        students[4].addMarks("Science", new double[]{40, 42, 39});
        students[4].addMarks("English", new double[]{45, 44, 46});
        students[4].calculateGPA();

        for (Student s : students) {
            s.generateReportCard();
        }

        Student.getTopPerformers(students, 3);
        System.out.printf("\nSchool Average GPA: %.2f\n", Student.calculateClassAverage(students));
        Student.generateSchoolReport(students);
    }
}