package com.example.SpringSecurityProject.Security.Services;

import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import com.example.SpringSecurityProject.Security.Entities.VerificationToken;

public interface UserService {
    //Registering Phase
    UserEntity registerUser(String firstName, String lastName, String email, String password, String username);

    //Token phase
    void saveVerificationTokenForUser(String verifyToken, UserEntity user);

    String validateVerification(String token);

    //Token re-send phase

    VerificationToken generateNewVerificationToken(String oldToken);
}
