package dao;

// DAO-> Data Access Object
import models.University;
import java.util.List;

public interface UniversityDAO {
    void addUniversity(University university);

    void updateUniversity(String name, University university);

    University searchUniversity(String name);

    void deleteUniversity(String name);

    List<University> getAllUniversities();

    void removeStudent(String enrollment);

    void removeStudentFromCourse(int courseId, String enrollment);
}