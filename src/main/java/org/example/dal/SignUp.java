package org.example.dal;
import org.example.pl.User;
import org.example.dto.SignUpDto;
import org.example.pl.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUp {
    private static final Logger logger = Logger.getLogger(SignUp.class.getName());

    public boolean registerUser(SignUpDto signUpDto) {
        String sql = "INSERT INTO users (username, password, email, role, address) VALUES (?, ?, ?, ?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (connection == null || connection.isClosed()) {
                logger.severe("Connection is closed or null.");
                return false;
            }
            preparedStatement.setString(1, signUpDto.getUsername());
            preparedStatement.setString(2, signUpDto.getPassword());
            preparedStatement.setString(3, signUpDto.getEmail());
            preparedStatement.setString(4, signUpDto.getRole());
            preparedStatement.setString(5, signUpDto.getAddress());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User registered successfully.");
                return true;
            } else {
                logger.warning("User registration failed.");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e.getMessage());
            return false;
        }
    }
    public int getUserId(String username) {
    String sql = "SELECT id FROM users WHERE username = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
    } catch (SQLException e) {
        logger.log(Level.WARNING, "User registration failed.", e.getMessage());
    }
    return -1; 
}
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception occurred", e.getMessage());
        }
        return users;
    }

    public User getUserById(Integer userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("role")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed to retrieve user.", e.getMessage());
        }
        return null;
    }
}