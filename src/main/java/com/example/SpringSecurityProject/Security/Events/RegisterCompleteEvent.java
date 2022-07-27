package com.example.SpringSecurityProject.Security.Events;

import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegisterCompleteEvent extends ApplicationEvent {
    private UserEntity user;

    private String applicationUrl;

    public RegisterCompleteEvent(UserEntity user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
