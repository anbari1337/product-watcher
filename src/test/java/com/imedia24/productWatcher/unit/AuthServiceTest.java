package com.imedia24.productWatcher.unit;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import com.imedia24.productWatcher.core.exception.ControllerException;
import com.imedia24.productWatcher.dtos.RegisterUserRequest;
import com.imedia24.productWatcher.entities.User;
import com.imedia24.productWatcher.repositories.UserRepository;
import com.imedia24.productWatcher.services.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
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

    public static final String MOCK_EMAIL = "amine@gmail.com";
    public static final String MOCK_NAME = "Amine Anbari";
    public static final String RAW_PASSWORD = "password123";
    public static final String ENCODED_PASSWORD = "encoded_password";

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
