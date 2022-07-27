package com.example.SpringSecurityProject.Security.Repositories;

import com.example.SpringSecurityProject.Security.Entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}
