package com.livo.user_service.user;

import com.livo.user_service.user.dto.UserRegisterDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<?> register(UserRegisterDTO dto){
        System.out.println("iniciou1");
        if (this.userRepository.findByEmail(dto.email()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }
        System.out.println("iniciou2");
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
