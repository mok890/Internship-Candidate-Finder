package service;

import models.Course;
import models.Student;
import models.University;

import java.util.ArrayList;
import java.util.List;

public class InternshipCandidateFinder {
    private UniversityManagement universityManagement;

    public InternshipCandidateFinder(UniversityManagement universityManagement) {
        this.universityManagement = universityManagement;
    }

    public List<Student> findCandidates(String state, String city, String courseOrArea) {
        List<Student> candidates = new ArrayList<>();
        for (University uni : universityManagement.getAllUniversities()) {
            if (uni.getAddress().getState().equals(state) && uni.getAddress().getCity().equals(city)) {
                for (Course course : uni.getCourses()) {
                    if (course.getName().equals(courseOrArea) || course.getArea().equals(courseOrArea)) {

                        candidates.addAll(course.getStudents());
                        System.out.println(course.getName() + " imk " + course.getArea() + "  " + uni.getName() + " "
                                + uni.getClass());
                    }
                }
            }
        }
        return candidates;
    }
}