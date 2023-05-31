//package ru.itis.springsemwork.security.rest.filters;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import ru.itis.springsemwork.dto.SignInForm;
//import ru.itis.springsemwork.models.User;
//import ru.itis.springsemwork.repositories.UsersRepository;
//import ru.itis.springsemwork.security.details.UserAccountDetails;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//
//@Slf4j
//public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    public static final String TOKEN = "token";
//    private final ObjectMapper objectMapper;
//    private final UsersRepository usersRepository;
//
//    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, UsersRepository usersRepository) {
//        super(authenticationManager);
//        this.objectMapper = objectMapper;
//        this.usersRepository = usersRepository;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            SignInForm form = objectMapper.readValue(request.getReader(), SignInForm.class);
//            log.info("Attempt authentication - email {}, password {}", form.getEmail(), form.getPassword());
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(form.getEmail(),
//                    form.getPassword());
//            return super.getAuthenticationManager().authenticate(token);
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException {
//        UserAccountDetails userDetails = (UserAccountDetails) authResult.getPrincipal();
//        User user = userDetails.getAccount();
//
//        String SECRET_KEY_ACCESS = "secret_key_access";
//        String token = JWT.create()
//                .withSubject(user.getId().toString())
//                .withClaim("email", user.getEmail())
//                .withClaim("role", user.getRole().toString())
//                .withClaim("state", user.getState().toString())
//                .sign(Algorithm.HMAC256(SECRET_KEY_ACCESS));
//        user.getToken().setAccessToken(token);
//        usersRepository.save(user);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        objectMapper.writeValue(response.getWriter(), Collections.singletonMap(TOKEN, token));
//    }
//}