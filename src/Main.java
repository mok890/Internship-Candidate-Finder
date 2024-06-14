import models.Address;
import models.Course;
import models.Student;
import models.University;
import dao.UniversityDAOImpl;
import service.UniversityManagement;
import service.InternshipCandidateFinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/internship_finder_new",
                    "root",
                    "Om#*#12345");

            // Initialize DAO and Service classes
            UniversityDAOImpl universityDAO = new UniversityDAOImpl(connection);
            UniversityManagement universityManagement = new UniversityManagement(universityDAO);

            // Menu loop
            boolean exit = false;
            while (!exit) {
                System.out.println("Select an option:");
                System.out.println("1) Add Student");
                System.out.println("2) Add Course");
                System.out.println("3) Add University");
                System.out.println("4) Search for Students");
                System.out.println("5) Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        addCourse(scanner);
                        break;
                    case 3:
                        addUniversity(scanner, universityManagement);
                        break;
                    case 4:
                        searchForStudents(scanner, universityManagement);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

            // Close the database connection
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.println("Enter student name:");
        String name = scanner.nextLine();
        System.out.println("Enter student enrollment number:");
        String enrollment = scanner.nextLine();
        System.out.println("Enter student date of birth (yyyy-mm-dd):");
        String dateOfBirth = scanner.nextLine();
        System.out.println("Enter year of enrollment:");
        int yearOfEnrollment = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter student situation:");
        String situation = scanner.nextLine();

        Student student = new Student(name, enrollment, dateOfBirth, yearOfEnrollment, situation);
        // Add student to a course (requires more logic)
        System.out.println("Student added successfully.");
    }

    private static void addCourse(Scanner scanner) {
        System.out.println("Enter course name:");
        String name = scanner.nextLine();
        System.out.println("Enter course acronym:");
        String acronym = scanner.nextLine();
        System.out.println("Enter course area:");
        String area = scanner.nextLine();

        Course course = new Course(name, acronym, area);
        // Add course to a university (requires more logic)
        System.out.println("Course added successfully.");
    }

    private static void addUniversity(Scanner scanner, UniversityManagement universityManagement) {
        System.out.println("Enter university name:");
        String name = scanner.nextLine();
        System.out.println("Enter university address:");
        String address = scanner.nextLine();
        System.out.println("Enter university district:");
        String district = scanner.nextLine();
        System.out.println("Enter university city:");
        String city = scanner.nextLine();
        System.out.println("Enter university state:");
        String state = scanner.nextLine();

        Address addressObj = new Address(address, district, city, state);
        University university = new University(name, addressObj);
        universityManagement.addUniversity(university);
        System.out.println("University added successfully.");
    }

    private static void searchForStudents(Scanner scanner, UniversityManagement universityManagement) {
        System.out.println("Enter state:");
        String state = scanner.nextLine();
        System.out.println("Enter city:");
        String city = scanner.nextLine();
        System.out.println("Enter course or area:");
        String courseOrArea = scanner.nextLine();

        InternshipCandidateFinder finder = new InternshipCandidateFinder(universityManagement);
        List<Student> candidates = finder.findCandidates(state, city, courseOrArea);

        System.out.println("Candidates found:");
        for (Student candidate : candidates) {
            System.out.println("Name: " + candidate.getName() + ", Enrollment: " + candidate.getEnrollment());
        }
    }
}