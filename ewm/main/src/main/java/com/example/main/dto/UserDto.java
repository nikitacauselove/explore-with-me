package com.example.main.dto;

import lombok.Data;

@Data
public class UserDto {
    private final Long id;
    private final String email;
    private final String name;
}
