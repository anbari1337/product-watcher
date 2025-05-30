package com.imedia24.productWatcher.services;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import com.imedia24.productWatcher.core.exception.ControllerException;
import com.imedia24.productWatcher.dtos.LoginRequest;
import com.imedia24.productWatcher.dtos.RegisterUserRequest;
import com.imedia24.productWatcher.entities.User;
import com.imedia24.productWatcher.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public String loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()));

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        return  jwtService.generateAccessToken(user);
    }

    public User registerUser(RegisterUserRequest request) throws ControllerException {
        var user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()) {
            throw new ControllerException(ErrorConstant.E_USER_ALREADY_REGISTERED);
        }
        var newUser = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .build();
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return newUser;
    }
}
