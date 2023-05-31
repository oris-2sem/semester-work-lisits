package ru.itis.springsemwork.dto.converters;

import org.mapstruct.Mapper;
import ru.itis.springsemwork.dto.UserDto;
import ru.itis.springsemwork.models.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserDto from(User user);
    List<UserDto> from(List<User> users);
}
