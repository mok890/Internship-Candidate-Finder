package models;

import java.util.ArrayList;
import java.util.List;

public class University {
    private String name;
    private Address address;
    private List<Course> courses;

    public University(String name, Address address) {
        this.name = name;
        this.address = address;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(String acronym) {
        courses.removeIf(course -> course.getAcronym().equals(acronym));
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
