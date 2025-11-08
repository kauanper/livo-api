package com.livo.user_service.user;

import com.livo.user_service.user.dto.UserAuthRequest;
import com.livo.user_service.user.dto.UserAuthResponse;
import com.livo.user_service.user.dto.UserDto;
import com.livo.user_service.user.dto.UserRegisterDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    /**
     * Autentica um usuário validando email e senha.
     * 
     * @param request Requisição contendo email e senha
     * @return ResponseEntity com UserAuthResponse se autenticação bem-sucedida
     * @throws org.springframework.web.server.ResponseStatusException se credenciais inválidas
     */
    public ResponseEntity<UserAuthResponse> authenticate(UserAuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email());
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        User user = userOpt.get();
        
        // Valida senha usando BCrypt
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Retorna dados do usuário sem senha
        UserAuthResponse response = new UserAuthResponse(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.getProfilePictureUrl()
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Busca usuário por ID.
     * 
     * @param id UUID do usuário
     * @return ResponseEntity com UserDto se encontrado
     * @throws org.springframework.web.server.ResponseStatusException se não encontrado
     */
    public ResponseEntity<UserDto> getById(UUID id) {
        Optional<User> userOpt = userRepository.findById(id);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        User user = userOpt.get();
        UserDto dto = toUserDto(user);
        
        return ResponseEntity.ok(dto);
    }

    /**
     * Busca usuário por email.
     * 
     * @param email Email do usuário
     * @return ResponseEntity com UserDto se encontrado
     * @throws org.springframework.web.server.ResponseStatusException se não encontrado
     */
    public ResponseEntity<UserDto> getByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        User user = userOpt.get();
        UserDto dto = toUserDto(user);
        
        return ResponseEntity.ok(dto);
    }

    /**
     * Converte entidade User para UserDto (sem senha).
     */
    private UserDto toUserDto(User user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getProfilePictureUrl()
        );
    }

}
