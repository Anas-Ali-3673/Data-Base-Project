package org.example.bll;

import org.example.dto.SignInDto;
import org.example.dal.SignIn;

public class SignInUser {
    private final SignIn signIn;
    public SignInUser(SignIn signIn) {
        this.signIn = signIn;

    }
    public boolean loginUser(SignInDto signInDto) {
        return signIn.signInUser(signInDto);
    }


}
