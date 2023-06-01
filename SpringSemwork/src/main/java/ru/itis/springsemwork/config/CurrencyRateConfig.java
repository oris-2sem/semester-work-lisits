package ru.itis.springsemwork.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class CurrencyRateConfig {

    public String getRates() {

        Map<String, Double> currencies = new LinkedHashMap<>();

        String jsonData = null;

        try {
            URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            String jsonResponse = response.toString();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            JsonObject valuteObject = jsonObject.getAsJsonObject("Valute");
            String usdCharCode = valuteObject.getAsJsonObject("USD").get("CharCode").getAsString();
            double usdValue = valuteObject.getAsJsonObject("USD").get("Value").getAsDouble();
            String eurCharCode = valuteObject.getAsJsonObject("EUR").get("CharCode").getAsString();
            double eurValue = valuteObject.getAsJsonObject("EUR").get("Value").getAsDouble();
            String bynCharCode = valuteObject.getAsJsonObject("BYN").get("CharCode").getAsString();
            double bynValue = valuteObject.getAsJsonObject("BYN").get("Value").getAsDouble();

            currencies.put("RUB", 1.0);
            currencies.put(usdCharCode, usdValue);
            currencies.put(eurCharCode, eurValue);
            currencies.put(bynCharCode, bynValue);

            Gson gson = new Gson();
            jsonData = gson.toJson(currencies);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}

