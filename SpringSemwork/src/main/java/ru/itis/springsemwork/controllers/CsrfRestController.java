package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Controller;
import ru.itis.springsemwork.controllers.api.CsrfApi;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class CsrfRestController implements CsrfApi {

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    @Override
    public ResponseEntity<?> myEndpoint(HttpServletRequest request) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
        String token = csrfToken.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-CSRF-TOKEN", token);
        return ResponseEntity.ok().headers(headers).build();
    }
}