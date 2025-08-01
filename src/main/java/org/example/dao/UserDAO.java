package org.example.dao;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class UserDAO {

    @Autowired
    private Connection connection;

    // --- CREATE ---
    public void save(User user) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users(name, email) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();

            // Get generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1)); // 🟢 Set the generated ID to the user object
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- UPDATE ---
    public void update(User user) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE users SET name=?, email=? WHERE id=?")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- DELETE ---
    public void delete(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM users WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- READ ONE ---
    public User get(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users WHERE id=?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- READ ALL ---
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}