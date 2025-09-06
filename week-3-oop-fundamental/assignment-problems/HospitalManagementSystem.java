import java.util.*;

public class HospitalManagementSystem {

    static String hospitalName = "CityCare Hospital";
    static int totalPatients = 0;
    static int totalAppointments = 0;
    static double totalRevenue = 0.0;

    static class Patient {
        String patientId, patientName, gender, contactInfo;
        int age;
        List<String> medicalHistory = new ArrayList<>();
        List<String> currentTreatments = new ArrayList<>();
        boolean isDischarged = false;

        public Patient(String id, String name, int age, String gender, String contact) {
            this.patientId = id;
            this.patientName = name;
            this.age = age;
            this.gender = gender;
            this.contactInfo = contact;
            totalPatients++;
        }

        public void updateTreatment(String treatment) {
            currentTreatments.add(treatment);
            System.out.printf("Treatment '%s' added for %s\n", treatment, patientName);
        }

        public void dischargePatient() {
            isDischarged = true;
            medicalHistory.addAll(currentTreatments);
            currentTreatments.clear();
            System.out.printf("%s has been discharged.\n", patientName);
        }

        public String toString() {
            return String.format("%s (%s) - Age: %d, Gender: %s", patientName, patientId, age, gender);
        }
    }

    static class Doctor {
        String doctorId, doctorName, specialization;
        String[] availableSlots;
        int patientsHandled = 0;
        double consultationFee;

        public Doctor(String id, String name, String specialization, String[] slots, double fee) {
            this.doctorId = id;
            this.doctorName = name;
            this.specialization = specialization;
            this.availableSlots = slots;
            this.consultationFee = fee;
        }

        public boolean isSlotAvailable(String time) {
            for (String slot : availableSlots) {
                if (slot.equals(time)) return true;
            }
            return false;
        }

        public void occupySlot(String time) {
            for (int i = 0; i < availableSlots.length; i++) {
                if (availableSlots[i].equals(time)) {
                    availableSlots[i] = "BOOKED";
                    patientsHandled++;
                    break;
                }
            }
        }

        public String toString() {
            return String.format("Dr. %s (%s) - %s", doctorName, doctorId, specialization);
        }
    }

    static class Appointment {
        String appointmentId, appointmentDate, appointmentTime, status, type;
        Patient patient;
        Doctor doctor;

        public Appointment(String id, Patient p, Doctor d, String date, String time, String type) {
            this.appointmentId = id;
            this.patient = p;
            this.doctor = d;
            this.appointmentDate = date;
            this.appointmentTime = time;
            this.status = "Scheduled";
            this.type = type;
            totalAppointments++;
        }

        public void cancelAppointment() {
            status = "Cancelled";
            System.out.printf("Appointment %s has been cancelled.\n", appointmentId);
        }

        public double generateBill() {
            double bill = switch (type.toLowerCase()) {
                case "consultation" -> doctor.consultationFee;
                case "follow-up" -> doctor.consultationFee * 0.5;
                case "emergency" -> doctor.consultationFee * 1.5;
                default -> doctor.consultationFee;
            };
            totalRevenue += bill;
            System.out.printf("Generated bill for %s: $%.2f (%s)\n", patient.patientName, bill, type);
            return bill;
        }

        public String toString() {
            return String.format("Appointment [%s] - %s with Dr. %s at %s on %s (%s)", appointmentId,
                    patient.patientName, doctor.doctorName, appointmentTime, appointmentDate, status);
        }
    }

    static class Hospital {
        List<Patient> patients = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();
        List<Appointment> appointments = new ArrayList<>();

        public void addPatient(Patient p) {
            patients.add(p);
        }

        public void addDoctor(Doctor d) {
            doctors.add(d);
        }

        public Appointment scheduleAppointment(Patient p, Doctor d, String date, String time, String type) {
            if (!d.isSlotAvailable(time)) {
                System.out.println("Selected time slot is not available.");
                return null;
            }
            String appointmentId = "A" + (appointments.size() + 1);
            Appointment a = new Appointment(appointmentId, p, d, date, time, type);
            appointments.add(a);
            d.occupySlot(time);
            System.out.printf("Appointment scheduled: %s with %s at %s\n", p.patientName, d.doctorName, time);
            return a;
        }

        public static void generateHospitalReport(List<Patient> patients, List<Appointment> appointments) {
            System.out.println("\n--- Hospital Report ---");
            System.out.printf("Total Patients: %d\n", patients.size());
            System.out.printf("Total Appointments: %d\n", appointments.size());
            System.out.printf("Total Revenue: $%.2f\n", totalRevenue);
        }

        public static void getDoctorUtilization(List<Doctor> doctors) {
            System.out.println("\n--- Doctor Utilization Report ---");
            for (Doctor d : doctors) {
                System.out.printf("%s - Patients Handled: %d\n", d.doctorName, d.patientsHandled);
            }
        }

        public static void getPatientStatistics(List<Patient> patients) {
            System.out.println("\n--- Patient Statistics ---");
            long active = patients.stream().filter(p -> !p.isDischarged).count();
            long discharged = patients.size() - active;
            System.out.printf("Active Patients: %d\n", active);
            System.out.printf("Discharged Patients: %d\n", discharged);
        }
    }

    public static void main(String[] args) {
        Hospital hospital = new Hospital();

        // Create Doctors
        Doctor drSmith = new Doctor("D001", "Smith", "Cardiology", new String[]{"10:00", "11:00", "12:00"}, 100);
        Doctor drLee = new Doctor("D002", "Lee", "Neurology", new String[]{"14:00", "15:00", "16:00"}, 120);
        hospital.addDoctor(drSmith);
        hospital.addDoctor(drLee);

        // Create Patients
        Patient p1 = new Patient("P001", "Alice", 30, "Female", "555-1234");
        Patient p2 = new Patient("P002", "Bob", 45, "Male", "555-5678");
        hospital.addPatient(p1);
        hospital.addPatient(p2);

        // Schedule Appointments
        Appointment a1 = hospital.scheduleAppointment(p1, drSmith, "2025-09-01", "10:00", "Consultation");
        Appointment a2 = hospital.scheduleAppointment(p2, drLee, "2025-09-01", "15:00", "Emergency");

        // Treatments
        p1.updateTreatment("Blood Pressure Monitoring");
        p2.updateTreatment("CT Scan");

        // Billing
        if (a1 != null) a1.generateBill();
        if (a2 != null) a2.generateBill();

        // Discharge one patient
        p1.dischargePatient();

        // Reports
        Hospital.generateHospitalReport(hospital.patients, hospital.appointments);
        Hospital.getDoctorUtilization(hospital.doctors);
        Hospital.getPatientStatistics(hospital.patients);

        // View appointments
        System.out.println("\n--- All Appointments ---");
        for (Appointment a : hospital.appointments) {
            System.out.println(a);
        }
    }
}