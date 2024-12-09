package org.example.bll;

import org.example.dto.SignInDto;
import org.example.dal.SignIn;
import org.example.pl.User;

public class SignInUser {
    private final SignIn signIn;
    public SignInUser(SignIn signIn) {
        this.signIn = signIn;

    }
    public User loginUser(String username, String password) {
        return signIn.signInUser(username, password);
    }

    public String getUserRole(String username) {
        return signIn.getUserRole(username);
    }
    public boolean changePassword(String username, String newPassword) {
        return signIn.changePassword(username, newPassword);
    }

    public int getUserId(String username) {
        return signIn.getUserId(username);
    }

}
