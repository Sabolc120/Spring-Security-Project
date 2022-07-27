package com.example.SpringSecurityProject.Security.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION_TIME = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne
    @JoinColumn(name="userId", nullable = false, foreignKey = @ForeignKey(name ="USER_VERIFY_TOKEN"))
    private UserEntity user;

    public VerificationToken(String token, UserEntity user) {
        this.token = token;
        this.expirationTime = calcExpire(EXPIRATION_TIME);
        this.user = user;
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = calcExpire(EXPIRATION_TIME);
    }

    private Date calcExpire(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
