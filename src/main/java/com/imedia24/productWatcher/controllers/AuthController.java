package com.imedia24.productWatcher.controllers;

import com.imedia24.productWatcher.core.constant.Paths;
import com.imedia24.productWatcher.core.constant.ErrorConstant;
import com.imedia24.productWatcher.core.exception.ControllerException;
import com.imedia24.productWatcher.core.exception.ExceptionResponseBuilder;
import com.imedia24.productWatcher.dtos.*;
import com.imedia24.productWatcher.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;


    @PostMapping(Paths.Auth.LOGIN)
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {
       String token = authService.loginUser(loginRequest);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(Paths.Auth.REGISTER)
    public ResponseEntity<UserDto> registerUser(
            @Valid @RequestBody RegisterUserRequest request
    ) throws ControllerException {
        var newUser = authService.registerUser(request);
        var userDto = new UserDto(newUser.getId(), newUser.getFullName(), newUser.getEmail());
        return ResponseEntity.ok(userDto);
    }


    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ExceptionResponse> badCredentialsExceptionHandler(HttpServletRequest req) {
        var exceptionResponse = ExceptionResponseBuilder.build(req);
        exceptionResponse.setError(ErrorConstant.E_BAD_CREDENTIALS_ERROR);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }
}
