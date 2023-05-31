//package ru.itis.springsemwork.services;
//
//import io.jsonwebtoken.Claims;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import ru.itis.springsemwork.models.User;
//import ru.itis.springsemwork.security.jwt.JwtAuthentication;
//import ru.itis.springsemwork.security.jwt.JwtProvider;
//import ru.itis.springsemwork.security.jwt.JwtRequest;
//import ru.itis.springsemwork.security.jwt.JwtResponse;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final UsersService usersService;
//    private final Map<String, String> refreshStorage = new HashMap<>();
//    private final JwtProvider jwtProvider;
//
//    public JwtResponse login(@NonNull JwtRequest authRequest) {
//        final User user = usersService.getByEmail(authRequest.getEmail());
//        if (user.getHashPassword().equals(authRequest.getPassword())) {
//            final String accessToken = jwtProvider.generateAccessToken(user);
//            final String refreshToken = jwtProvider.generateRefreshToken(user);
//            refreshStorage.put(user.getEmail(), refreshToken);
//            return new JwtResponse(accessToken, refreshToken);
//        } else {
//            throw new AuthenticationException("Неправильный пароль") {};
//        }
//    }
//
//    public JwtResponse getAccessToken(@NonNull String refreshToken) {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
//            final String email = claims.getSubject();
//            final String saveRefreshToken = refreshStorage.get(email);
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final User user = usersService.getByEmail(email);
//                final String accessToken = jwtProvider.generateAccessToken(user);
//                return new JwtResponse(accessToken, null);
//            }
//        }
//        return new JwtResponse(null, null);
//    }
//
//    public JwtResponse refresh(@NonNull String refreshToken) {
//        if (jwtProvider.validateRefreshToken(refreshToken)) {
//            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
//            final String email = claims.getSubject();
//            final String saveRefreshToken = refreshStorage.get(email);
//            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
//                final User user = usersService.getByEmail(email);
//                final String accessToken = jwtProvider.generateAccessToken(user);
//                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
//                refreshStorage.put(user.getEmail(), newRefreshToken);
//                return new JwtResponse(accessToken, newRefreshToken);
//            }
//        }
//        throw new AuthenticationException("Невалидный JWT токен") {};
//    }
//
//    public JwtAuthentication getAuthInfo() {
//        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
//    }
//}