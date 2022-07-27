package com.example.SpringSecurityProject.Security.Repositories;

import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByUsername(String username);
}
