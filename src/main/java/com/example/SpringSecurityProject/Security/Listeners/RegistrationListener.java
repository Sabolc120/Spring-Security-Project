package com.example.SpringSecurityProject.Security.Listeners;

import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import com.example.SpringSecurityProject.Security.Events.RegisterCompleteEvent;
import com.example.SpringSecurityProject.Security.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationListener implements ApplicationListener<RegisterCompleteEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegisterCompleteEvent event) {
        UserEntity user = event.getUser();
        String verifyToken = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(verifyToken, user);
        String url = event.getApplicationUrl()+"/verifyReg?token="+verifyToken;
        log.info("Click here to verify your account: {}", url);
    }
}
