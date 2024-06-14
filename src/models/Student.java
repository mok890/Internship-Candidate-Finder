package models;

public class Student {
    private String name;
    private String enrollment;
    private String dateOfBirth;
    private int yearOfEnrollment;
    private String situation;

    public Student(String name, String enrollment, String dateOfBirth, int yearOfEnrollment, String situation) {
        this.name = name;
        this.enrollment = enrollment;
        this.dateOfBirth = dateOfBirth;
        this.yearOfEnrollment = yearOfEnrollment;
        this.situation = situation;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getYearOfEnrollment() {
        return yearOfEnrollment;
    }

    public void setYearOfEnrollment(int yearOfEnrollment) {
        this.yearOfEnrollment = yearOfEnrollment;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}
