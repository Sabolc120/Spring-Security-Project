package com.example.SpringSecurityProject.Security.Services.Implementations;

import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import com.example.SpringSecurityProject.Security.Entities.VerificationToken;
import com.example.SpringSecurityProject.Security.Repositories.UserRepo;
import com.example.SpringSecurityProject.Security.Repositories.VerificationTokenRepo;
import com.example.SpringSecurityProject.Security.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    //Registering Phase
    @Override
    public UserEntity registerUser(String firstName, String lastName, String email, String password, String username) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setPassword(passwordEncoder.encode(password));
        user.setLastName(lastName);
        user.setAuthorities("GUEST");
        user.setUsername(username);
        return user;
    }

    //Verify phase
    @Override
    public void saveVerificationTokenForUser(String verifyToken, UserEntity user) {
        VerificationToken verificationToken = new VerificationToken(verifyToken, user);

        verificationTokenRepo.save(verificationToken);
    }

    @Override
    public String validateVerification(String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
        if(verificationToken == null){
            return "invalid";
        }
        UserEntity user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime() <= 0)){
            verificationTokenRepo.delete(verificationToken);
            return"expired";
        }
        user.setEnabled(true);
        user.setAuthorities("USER");
        userRepo.save(user);
        return "valid";
    }
    //Generate new token phase
    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepo.save(verificationToken);
        UserEntity user = verificationToken.getUser();
        user.setEnabled(true);
        user.setAuthorities("USER");
        userRepo.save(user);
        return verificationToken;
    }
}
