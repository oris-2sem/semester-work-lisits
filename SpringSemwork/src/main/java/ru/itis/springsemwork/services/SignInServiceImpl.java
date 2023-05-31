package ru.itis.springsemwork.services;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.dto.SignInForm;
import ru.itis.springsemwork.models.User;
import ru.itis.springsemwork.repositories.UsersRepository;


@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService{

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User signIn(SignInForm signInForm) {
        User user = usersRepository.findByEmail(signInForm.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User with email " + signInForm.getEmail() + " not found"));
//        if (user != null) {
//            String token = JWT.create()
//                    .withSubject(user.getId().toString())
//                    .withClaim("email", user.getEmail())
//                    .withClaim("role", user.getRole().toString())
//                    .withClaim("state", user.getState().toString())
//                    .sign(Algorithm.HMAC256("secret_key_tea_access"));
//            user.setAccessToken(token);
//        }

        return user;
    }
}
