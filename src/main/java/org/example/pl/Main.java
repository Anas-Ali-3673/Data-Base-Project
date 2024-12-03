package org.example.pl;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection dbConnection = (DatabaseConnection) DatabaseConnection.getConnection();
            Connection connection = dbConnection.getConnection();

            if (connection != null) {
                System.out.println("Successfully connected to the Oracle Database!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
