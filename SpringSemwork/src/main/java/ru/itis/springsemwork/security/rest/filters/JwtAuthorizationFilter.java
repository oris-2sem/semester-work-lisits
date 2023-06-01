//package ru.itis.springsemwork.security.rest.filters;
//
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import ru.itis.springsemwork.security.rest.jwt.AuthorizationHeaderUtil;
//import ru.itis.springsemwork.security.rest.jwt.JwtUtil;
//
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static ru.itis.springsemwork.security.rest.filters.JwtAuthenticationFilter.AUTHENTICATION_URL;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthorizationFilter extends OncePerRequestFilter {
//
//    private final AuthorizationHeaderUtil authorizationHeaderUtil;
//
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (request.getServletPath().equals(AUTHENTICATION_URL)) {
//            filterChain.doFilter(request, response);
//        } else {
//            if (authorizationHeaderUtil.hasAuthorizationToken(request)) {
//                String jwt = authorizationHeaderUtil.getToken(request);
//                try {
//                    Authentication authentication = jwtUtil.buildAuthentication(jwt);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    filterChain.doFilter(request, response);
//                } catch (JWTVerificationException e) {
//                    logger.info(e.getMessage());
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                }
//        } else{
//            filterChain.doFilter(request, response);
//        }
//    }
//}
//}
