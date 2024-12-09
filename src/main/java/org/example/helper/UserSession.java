package org.example.helper;

public class UserSession {
    private static UserSession instance;
    public int userId;
    private String username;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Get the single instance of UserSession
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set user details
    public void setUserDetails(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    // Getters for user details
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    // Clear session when user logs out
    public void clearSession() {
        userId = 0;
        username = null;
    }
}