//package main;

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

public class Main {
    public static void main(String[] args) {
        try {
            // Database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_db", "root",
                    "");

            // Initialize DAO and Service classes
            UniversityDAOImpl universityDAO = new UniversityDAOImpl(connection);
            UniversityManagement universityManagement = new UniversityManagement(universityDAO);

            // Create and add Universities, Courses, and Students
            Address address1 = new Address("123 Main St", "Central District", "CityA", "StateX");
            University uni1 = new University("University A", address1);
            Course course1 = new Course("Computer Science", "CS", "Technology");
            Student student1 = new Student("Alice", "001", "1998-04-05", 2018, "Enrolled");

            course1.addStudent(student1);
            uni1.addCourse(course1);
            universityManagement.addUniversity(uni1);

            // Search for Candidates
            InternshipCandidateFinder finder = new InternshipCandidateFinder(universityManagement);
            List<Student> candidates = finder.findCandidates("StateX", "CityA", "Technology");

            // Print Candidates
            for (Student candidate : candidates) {
                System.out.println("Name: " + candidate.getName() + ", Enrollment: " + candidate.getEnrollment());
            }

            // Close the database connection
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}