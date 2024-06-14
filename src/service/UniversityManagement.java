package service;

import dao.UniversityDAO;
import models.University;

import java.util.List;

public class UniversityManagement {
    private UniversityDAO universityDAO;

    public UniversityManagement(UniversityDAO universityDAO) {
        this.universityDAO = universityDAO;
    }

    public void addUniversity(University university) {
        universityDAO.addUniversity(university);
    }

    public void updateUniversity(String name, University university) {
        universityDAO.updateUniversity(name, university);
    }

    public University searchUniversity(String name) {
        return universityDAO.searchUniversity(name);
    }

    public void deleteUniversity(String name) {
        universityDAO.deleteUniversity(name);
    }

    public List<University> getAllUniversities() {
        return universityDAO.getAllUniversities();
    }
}