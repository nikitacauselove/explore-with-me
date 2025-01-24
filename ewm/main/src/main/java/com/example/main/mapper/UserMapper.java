package com.example.main.mapper;

import com.example.main.dto.NewUserRequest;
import com.example.main.dto.UserDto;
import com.example.main.repository.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(NewUserRequest request);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);
}
