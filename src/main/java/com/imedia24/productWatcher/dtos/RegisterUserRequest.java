package com.imedia24.productWatcher.dtos;

import com.imedia24.productWatcher.core.constant.ErrorConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotBlank(message = ErrorConstant.E_NAME_BLANK)
    @NotEmpty(message = ErrorConstant.E_NAME_REQUIRED)
    String fullName;

    @NotEmpty(message = ErrorConstant.E_EMAIL_REQUIRED)
    @Email(message = ErrorConstant.E_EMAIL_INVALID)
    String email;

    @NotEmpty(message = ErrorConstant.E_PASSWORD_REQUIRED)
    String password;

}
