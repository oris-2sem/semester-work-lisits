package ru.itis.springsemwork.services;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.dto.SignUpForm;
import ru.itis.springsemwork.dto.UserDto;
import ru.itis.springsemwork.dto.converters.UserConverter;
import ru.itis.springsemwork.models.Order;
import ru.itis.springsemwork.models.Role;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.repositories.UsersRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserConverter userConverter;

    @Override
    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public UserDto addNewUser(SignUpForm signUpForm) {

        User user = User.builder()
                .email(signUpForm.getEmail())
                .name(signUpForm.getName())
                .password(signUpForm.getPassword())
//                .accessToken()
                .roles(Collections.singleton(Role.USER))
                .state(User.State.CONFIRMED)
                .personalDiscount(0)
                .build();
        usersRepository.save(user);

        return userConverter.from(user);
    }

    @Override
    public UserDto findById(Long id) {
        UserDto user = userConverter.from(usersRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not found")));
        return user;
    }

    @Override
    public User getByEmail(@NonNull String email) {
        User user = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        return user;
    }

    @Override
    public Long getTotalCount(List<Order> orders) {
        final Long[] totalCount = {0L};
        orders.forEach(order ->
                order.getItemsWithCount().forEach(orderItem ->
                        totalCount[0] += orderItem.getItemCount() * orderItem.getItemCost()));
        return totalCount[0];
    }

    @Override
    public void checkDiscount(User user, Long totalCount) {
        int discount = checkTotalCount(totalCount);
        if (user.getPersonalDiscount() != discount) {
            user.setPersonalDiscount(discount);
        }
    }

    @Override
    public User updateData(User user, SignUpForm signUpForm) {
//        if (signUpForm.getName() != null && !signUpForm.getName().equals(null)) {
        if (signUpForm.getName().length() > 0) {
            user.setName(signUpForm.getName());
            log.info("Name updated");
        }
//        if (signUpForm.getPassword() != null && !signUpForm.getPassword().equals(null)) {
        if (signUpForm.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
            log.info("Password updated");
        }
//        if (signUpForm.getEmail() != null && !signUpForm.getEmail().equals(null)) {
        if (signUpForm.getEmail().length() > 0) {
            user.setEmail(signUpForm.getEmail());
            log.info("Email updated");
        }
        usersRepository.save(user);
        return user;
    }


    private Integer checkTotalCount(Long totalCount) {
        int discount;
        if (totalCount <= 10000L) {
            discount = 0;
        } else if (totalCount <= 15000L) {
            discount = 3;
        } else if (totalCount <= 25000L) {
            discount = 5;
        } else if (totalCount <= 50000L) {
            discount = 10;
        } else if (totalCount <= 75000L) {
            discount = 15;
        } else if (totalCount <= 100000L) {
            discount = 20;
        } else {
            discount = 25;
        }
        return discount;
    }

}
