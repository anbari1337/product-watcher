package com.imedia24.productWatcher.dtos;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = ErrorConstant.E_EMAIL_REQUIRED)
    @Email
    private String email;
    @NotBlank(message = ErrorConstant.E_PASSWORD_REQUIRED)
    private String password;
}
