package dao;

import models.Address;
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
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, university.getName());
            pstmt.setString(2, university.getAddress().getAddress());
            pstmt.setString(3, university.getAddress().getDistrict());
            pstmt.setString(4, university.getAddress().getCity());
            pstmt.setString(5, university.getAddress().getState());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                        rs.getString("state")
                );
                return new University(rs.getString("name"), address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                        rs.getString("state")
                );
                universities.add(new University(rs.getString("name"), address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return universities;
    }
}