package ru.itis.springsemwork.validation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailValidator {
    private static final String API_KEY = "ce06360dcee760dc11e4138c78d65db6";

    public boolean validateEmail(String email) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://apilayer.net/api/check?access_key=" + API_KEY + "&email=" + email);

        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String result = EntityUtils.toString(entity);
//            if (result.)
            // Пример: проверка поля "format_valid" для определения валидности адреса
            // Верните true, если адрес валидный, и false в противном случае
            return true;
        }
        return false; // Возвращаем false по умолчанию, если не удалось проверить валидность
    }
}


//
//    String email = "test@example.com";
//    boolean isValid = EmailValidator.validateEmail(email);
//
//if (isValid) {
//        // Адрес электронной почты валидный
//        } else {
//        // Адрес электронной почты недействительный
//        }