package ru.itis.springsemwork.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CartCookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean cookieExists = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cartCookie")) {
                    cookieExists = true;
                    break;
                }
            }
        }

        if (!cookieExists) {
            Cookie cookie = new Cookie("cartCookie", UUID.randomUUID().toString());
            cookie.setMaxAge(24 * 60 * 60); // 24 hours
            response.addCookie(cookie);
        }
        return true;
    }
}
