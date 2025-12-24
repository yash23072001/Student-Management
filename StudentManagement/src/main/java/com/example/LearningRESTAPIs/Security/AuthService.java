package com.example.LearningRESTAPIs.Security;

import com.example.LearningRESTAPIs.DTO.LoginRequestDTO;
import com.example.LearningRESTAPIs.DTO.LoginResponseDTO;
import com.example.LearningRESTAPIs.DTO.SignUpResponseDto;
import com.example.LearningRESTAPIs.Entity.User;
import com.example.LearningRESTAPIs.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDTO(token, user.getId());
    }

    public @Nullable SignUpResponseDto signup(LoginRequestDTO signupRequestDTO) {
        User user = userRepository.findByUsername(signupRequestDTO.getUsername()).orElse(null);
        if (user != null) throw new IllegalArgumentException("user already exists");

        user = userRepository.save(User.builder()
                .username(signupRequestDTO.getUsername())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .build()
        );

        return new SignUpResponseDto(user.getId(), user.getUsername());

    }
}
















