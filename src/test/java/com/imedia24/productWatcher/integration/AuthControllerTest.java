package com.imedia24.productWatcher.integration;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import com.imedia24.productWatcher.core.constant.Paths;
import com.imedia24.productWatcher.dtos.LoginRequest;
import com.imedia24.productWatcher.dtos.RegisterUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerTest extends AbstractIntegrationTest {


    private final static String FULL_NAME = "Amine Anbari";
    private final static String EMAIL = "amine@gmail.com";
    private final static String PASSWORD = "password1.";


    @Test
    @DisplayName("Should register user successfully")
    void shouldRegisterUserSuccessfully() throws Exception {
        registerTestUser();
    }

    @Test
    @DisplayName("Should login user successfully")
    void shouldLoginUserSuccessfully() throws Exception {
        registerTestUser();

        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        performPostRequest(Paths.Auth.LOGIN, loginRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @DisplayName("Should return bad request if payload is invalid when registering a user")
    public void shouldReturnBadRequestIfPayloadIsInvalid() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFullName(FULL_NAME);
        request.setPassword(PASSWORD);

        performPostRequest(Paths.Auth.REGISTER, request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value(ErrorConstant.E_EMAIL_REQUIRED));

    }

    @Test
    @DisplayName("Should return unauthorized if user provide incorrect credentials in login")
    public void shouldReturnUnauthorizedIfUserProvideBadCredentialsInLogin() throws Exception {
        registerTestUser();
        LoginRequest loginRequest = new LoginRequest(EMAIL, "INCORRECT_PASSWORD");

        performPostRequest(Paths.Auth.LOGIN, loginRequest)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value(ErrorConstant.E_BAD_CREDENTIALS_ERROR));
    }

    private void registerTestUser() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest(
                FULL_NAME, EMAIL, PASSWORD
        );
        performPostRequest(Paths.Auth.REGISTER, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.email").value(EMAIL));
    }

}
