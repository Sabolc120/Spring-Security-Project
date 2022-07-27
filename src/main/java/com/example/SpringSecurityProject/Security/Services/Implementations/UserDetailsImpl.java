package com.example.SpringSecurityProject.Security.Services.Implementations;

import com.example.SpringSecurityProject.Security.Entities.UserEntity;
import com.example.SpringSecurityProject.Security.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepo.findUserByUsername(username);

        UserEntity u = user.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new SecurityUser(u);
    }
}
