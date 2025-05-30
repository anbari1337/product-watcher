package com.imedia24.productWatcher.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    Long id;
    String fullName;
    String email;
}
