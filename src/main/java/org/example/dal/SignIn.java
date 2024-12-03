package org.example.dal;

import org.example.dto.SignInDto;
import org.example.pl.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIn {
     public boolean signInUser(SignInDto signInDto) {
         String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, signInDto.getUsername());
                preparedStatement.setString(2, signInDto.getPassword());
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
     }
}
