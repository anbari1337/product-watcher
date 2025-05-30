package com.imedia24.productWatcher.core.exception;

import com.imedia24.productWatcher.dtos.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

public class ExceptionResponseBuilder {

    public static ExceptionResponse build(HttpServletRequest request) {
        return ExceptionResponse
                .builder()
                .url(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
