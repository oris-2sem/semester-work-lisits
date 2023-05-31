//package ru.itis.springsemwork.security.rest.filters;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import ru.itis.springsemwork.models.User;
//import ru.itis.springsemwork.repositories.UsersRepository;
//import ru.itis.springsemwork.security.details.UserAccountDetails;
//import ru.itis.springsemwork.security.rest.config.RestSecurityConfiguration;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//public class TokenAuthorizationFilter extends OncePerRequestFilter {
//
//    private final UsersRepository usersRepository;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getRequestURI().equals(RestSecurityConfiguration.LOGIN_FILTER_PROCESSES_URL)) {
//            filterChain.doFilter(request, response);
//        } else {
//            String tokenHeader = request.getHeader("Authorization");
//            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//                String token = tokenHeader.substring("Bearer ".length());
//
//                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret_key_refresh"))
//                        .build().verify(token);
//                Optional<User> user = usersRepository.findByRefreshToken(decodedJWT.getToken());
//
//                if (user.isPresent()) {
//                    UserAccountDetails userDetails = new UserAccountDetails(user.get());
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(token, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    filterChain.doFilter(request, response);
//                } else {
//                    logger.warn("Wrong token");
//                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    objectMapper.writeValue(response.getWriter(), Collections.singletonMap("error", "user not found with token"));
//                }
//            } else {
//                logger.warn("Token is missing");
//                filterChain.doFilter(request, response);
//            }
//        }
//    }
//}
