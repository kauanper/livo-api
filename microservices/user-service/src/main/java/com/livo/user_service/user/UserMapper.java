package com.livo.user_service.user;

import com.livo.user_service.user.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toEntity(UserRegisterDTO dto){
        User user = new User();
        user.setUsername(dto.name());
        user.setEmail(dto.email());
        String encryptedPassword = passwordEncoder.encode(dto.password());
        user.setPassword(encryptedPassword);
        user.setProfilePictureUrl(dto.profilePictureUrl());

        return user;
    }
}
