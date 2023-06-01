package ru.itis.springsemwork.services;

import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.dto.UserDto;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.Role;
import ru.itis.springsemwork.models.User;

import java.util.List;
import java.util.Set;

public interface UsersService {

    User findByEmail(String email);

    boolean existsByEmail (String email);

    UserDto addNewUser(SignUpForm signUpForm);

    UserDto findById(Long id);

    User getByEmail(String email);

//    List<Order> getUsersOrders(UserDto userDto);

    Long getTotalCount(List<Order> orders);

    void checkDiscount(User user, Long totalCount);

    User updateData(User user, SignUpForm signUpForm);
}
