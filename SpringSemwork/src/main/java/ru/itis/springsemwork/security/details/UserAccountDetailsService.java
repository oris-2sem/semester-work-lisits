package ru.itis.springsemwork.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.repositories.UsersRepository;

@Service
@RequiredArgsConstructor
public class UserAccountDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserAccountDetails(
                usersRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User not found")));
    }
}
