package models;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String acronym;
    private String area;
    private List<Student> students;

    public Course(String name, String acronym, String area) {
        this.name = name;
        this.acronym = acronym;
        this.area = area;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(String enrollment) {
        students.removeIf(student -> student.getEnrollment().equals(enrollment));
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}