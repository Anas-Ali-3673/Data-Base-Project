package org.example.bll;

import org.example.dto.SignUpDto;
import org.example.dal.SignUp; // Import the DAL layer

public class SignUpUser {
    private final SignUp dalSignUp; // Reference to the DAL SignUp class

    public SignUpUser() {
        this.dalSignUp = new SignUp(); // Initialize the DAL layer
    }

    public boolean registerUser(SignUpDto signUpDto) {
        return dalSignUp.registerUser(signUpDto); // Delegate to the DAL method
    }
}
