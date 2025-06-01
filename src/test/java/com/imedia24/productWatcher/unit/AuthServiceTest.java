package com.imedia24.productWatcher.unit;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import com.imedia24.productWatcher.core.exception.ControllerException;
import com.imedia24.productWatcher.dtos.LoginRequest;
import com.imedia24.productWatcher.dtos.RegisterUserRequest;
import com.imedia24.productWatcher.entities.User;
import com.imedia24.productWatcher.repositories.UserRepository;
import com.imedia24.productWatcher.services.AuthService;
import com.imedia24.productWatcher.services.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;


     private final String MOCK_EMAIL = "amine@gmail.com";
     private final String MOCK_NAME = "Amine Anbari";
     private final String RAW_PASSWORD = "password123";
     private final String ENCODED_PASSWORD = "encoded_password";

    @Test
    @DisplayName("Should login user successfully")
    void shouldLoginUserSuccessfully() {

        LoginRequest loginRequest = new LoginRequest(MOCK_EMAIL, RAW_PASSWORD);
        String ACCESS_TOKEN = "access_token";

        User mockUser = new User();
        mockUser.setEmail(MOCK_EMAIL);

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockUser));
        when(jwtService.generateAccessToken(mockUser)).thenReturn(ACCESS_TOKEN);

        String token = authService.loginUser(loginRequest);

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(MOCK_EMAIL, RAW_PASSWORD));
        verify(jwtService).generateAccessToken(mockUser);
        assertThat(token).isEqualTo(ACCESS_TOKEN);
    }

    @Test
    @DisplayName("Should throw an exception if the credentials provided are incorrect")
    void shouldThrowExceptionIfCredentialsAreIncorrect() {
        LoginRequest loginRequest = new LoginRequest(MOCK_EMAIL, RAW_PASSWORD);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()))
        ).thenThrow(new BadCredentialsException(ErrorConstant.E_BAD_CREDENTIALS_ERROR));

        BadCredentialsException exception = catchThrowableOfType(() -> authService.loginUser(loginRequest), BadCredentialsException.class);
        assertThat(exception)
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage(ErrorConstant.E_BAD_CREDENTIALS_ERROR);
    }

    @Test
    @DisplayName("Should create a new user successfully")
    void shouldRegisterUserWhenEmailIsNotAlreadyUsed() throws ControllerException {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest(MOCK_NAME, MOCK_EMAIL, RAW_PASSWORD);

        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(RAW_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Act
        User registeredUser = authService.registerUser(request);

        // Assert
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getEmail()).isEqualTo(MOCK_EMAIL);
        assertThat(savedUser.getFullName()).isEqualTo(MOCK_NAME);
        assertThat(savedUser.getPassword()).isEqualTo(ENCODED_PASSWORD);

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getEmail()).isEqualTo(MOCK_EMAIL);
    }

    @Test
    @DisplayName("Should throw an exception if email already exist")
    void shouldThrowExceptionWhenRegisteringWithExistingEmail() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest(MOCK_NAME, MOCK_EMAIL, ENCODED_PASSWORD);

        User existingUser = new User();
        existingUser.setEmail(MOCK_EMAIL);
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        ControllerException exception = catchThrowableOfType(() -> authService.registerUser(request), ControllerException.class);

        assertThat(exception)
                .isInstanceOf(ControllerException.class)
                .hasMessage(ErrorConstant.E_USER_ALREADY_REGISTERED);
    }
}
