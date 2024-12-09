package org.example.dal;

import org.example.dto.PromotionDto;
import org.example.pl.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDal {
    public boolean addPromotion(PromotionDto promotion) {
        String sql = "INSERT INTO promotions (code, discount_percentage, is_active, product_id, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, promotion.getCode());
            preparedStatement.setDouble(2, promotion.getDiscountPercentage());
            preparedStatement.setBoolean(3, promotion.isActive());
            if (promotion.getProductId() != null) {
                preparedStatement.setInt(4, promotion.getProductId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            if (promotion.getUserId() != null) {
                preparedStatement.setInt(5, promotion.getUserId());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
            }
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PromotionDto getPromotionByCode(String code) {
        String sql = "SELECT * FROM promotions WHERE code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new PromotionDto(
                        resultSet.getString("code"),
                        resultSet.getDouble("discount_percentage"),
                        resultSet.getBoolean("is_active"),
                        resultSet.getObject("product_id", Integer.class),
                        resultSet.getObject("user_id", Integer.class)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PromotionDto> getAllPromotions() {
        String sql = "SELECT * FROM promotions";
        List<PromotionDto> promotions = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                promotions.add(new PromotionDto(
                        resultSet.getString("code"),
                        resultSet.getDouble("discount_percentage"),
                        resultSet.getBoolean("is_active"),
                        resultSet.getObject("product_id", Integer.class),
                        resultSet.getObject("user_id", Integer.class)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public boolean updatePromotion(PromotionDto promotion) {
        String sql = "UPDATE promotions SET discount_percentage = ?, is_active = ?, product_id = ?, user_id = ? WHERE code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, promotion.getDiscountPercentage());
            preparedStatement.setBoolean(2, promotion.isActive());
            if (promotion.getProductId() != null) {
                preparedStatement.setInt(3, promotion.getProductId());
            } else {
                preparedStatement.setNull(3, Types.INTEGER);
            }
            if (promotion.getUserId() != null) {
                preparedStatement.setInt(4, promotion.getUserId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }
            preparedStatement.setString(5, promotion.getCode());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePromotion(String code) {
        String sql = "DELETE FROM promotions WHERE code = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}