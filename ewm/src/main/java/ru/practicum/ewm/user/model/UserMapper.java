package ru.practicum.ewm.user.model;

import org.mapstruct.Mapper;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(NewUserRequest request);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);
}
