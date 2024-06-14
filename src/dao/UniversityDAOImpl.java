package dao;

import models.Address;
import models.Course;
import models.Student;
import models.University;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniversityDAOImpl implements UniversityDAO {
    private Connection connection;

    public UniversityDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addUniversity(University university) {
        String sql = "INSERT INTO universities (name, address, district, city, state) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, university.getName());
            pstmt.setString(2, university.getAddress().getAddress());
            pstmt.setString(3, university.getAddress().getDistrict());
            pstmt.setString(4, university.getAddress().getCity());
            pstmt.setString(5, university.getAddress().getState());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int universityId = rs.getInt(1);
                addCourses(universityId, university.getCourses());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCourses(int universityId, List<Course> courses) throws SQLException {
        String courseSql = "INSERT INTO courses (name, acronym, area) VALUES (?, ?, ?)";
        String linkSql = "INSERT INTO university_courses (university_id, course_id) VALUES (?, ?)";
        try (PreparedStatement courseStmt = connection.prepareStatement(courseSql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement linkStmt = connection.prepareStatement(linkSql)) {
            for (Course course : courses) {
                courseStmt.setString(1, course.getName());
                courseStmt.setString(2, course.getAcronym());
                courseStmt.setString(3, course.getArea());
                courseStmt.executeUpdate();

                ResultSet rs = courseStmt.getGeneratedKeys();
                if (rs.next()) {
                    int courseId = rs.getInt(1);
                    addStudents(courseId, course.getStudents());

                    linkStmt.setInt(1, universityId);
                    linkStmt.setInt(2, courseId);
                    linkStmt.executeUpdate();
                }
            }
        }
    }

    private void addStudents(int courseId, List<Student> students) throws SQLException {
        String studentSql = "INSERT INTO students (name, enrollment, date_of_birth, year_of_enrollment, situation) VALUES (?, ?, ?, ?, ?)";
        String linkSql = "INSERT INTO course_students (course_id, student_id) VALUES (?, ?)";
        try (PreparedStatement studentStmt = connection.prepareStatement(studentSql, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement linkStmt = connection.prepareStatement(linkSql)) {
            for (Student student : students) {
                studentStmt.setString(1, student.getName());
                studentStmt.setString(2, student.getEnrollment());
                studentStmt.setDate(3, Date.valueOf(student.getDateOfBirth()));
                studentStmt.setInt(4, student.getYearOfEnrollment());
                studentStmt.setString(5, student.getSituation());
                studentStmt.executeUpdate();

                ResultSet rs = studentStmt.getGeneratedKeys();
                if (rs.next()) {
                    int studentId = rs.getInt(1);
                    linkStmt.setInt(1, courseId);
                    linkStmt.setInt(2, studentId);
                    linkStmt.executeUpdate();
                }
            }
        }
    }

    @Override
    public void updateUniversity(String name, University university) {
        String sql = "UPDATE universities SET address = ?, district = ?, city = ?, state = ? WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, university.getAddress().getAddress());
            pstmt.setString(2, university.getAddress().getDistrict());
            pstmt.setString(3, university.getAddress().getCity());
            pstmt.setString(4, university.getAddress().getState());
            pstmt.setString(5, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public University searchUniversity(String name) {
        String sql = "SELECT * FROM universities WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Address address = new Address(
                        rs.getString("address"),
                        rs.getString("district"),
                        rs.getString("city"),
                        rs.getString("state"));
                University university = new University(rs.getString("name"), address);
                university.setCourses(getCourses(rs.getInt("id")));
                return university;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Course> getCourses(int universityId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.acronym, c.area FROM courses c " +
                "JOIN university_courses uc ON c.id = uc.course_id WHERE uc.university_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, universityId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Course course = new Course(
                        rs.getString("name"),
                        rs.getString("acronym"),
                        rs.getString("area"));
                course.setStudents(getStudents(rs.getInt("id")));
                courses.add(course);
            }
        }
        return courses;
    }

    private List<Student> getStudents(int courseId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.id, s.name, s.enrollment, s.date_of_birth, s.year_of_enrollment, s.situation FROM students s "
                +
                "JOIN course_students cs ON s.id = cs.student_id WHERE cs.course_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("enrollment"),
                        rs.getDate("date_of_birth").toString(),
                        rs.getInt("year_of_enrollment"),
                        rs.getString("situation"));
                students.add(student);
            }
        }
        return students;
    }

    @Override
    public void deleteUniversity(String name) {
        String sql = "DELETE FROM universities WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<University> getAllUniversities() {
        List<University> universities = new ArrayList<>();
        String sql = "SELECT * FROM universities";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Address address = new Address(
                        rs.getString("address"),
                        rs.getString("district"),
                        rs.getString("city"),
                        rs.getString("state"));
                University university = new University(rs.getString("name"), address);
                university.setCourses(getCourses(rs.getInt("id")));
                universities.add(university);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return universities;
    }
}