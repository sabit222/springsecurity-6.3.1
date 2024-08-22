package com.propro.service;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {
    private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=6c3b3a28984244fbb32cafd80e14d7d7";

    public double convertToKZT(String baseCurrency, double amount) {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(API_URL, String.class);

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonObject rates = jsonObject.getAsJsonObject("rates");

        // Проверка на существование валют
        if (!rates.has(baseCurrency) || !rates.has("KZT")) {
            throw new IllegalArgumentException("Invalid currency code");
        }

        double baseRate = rates.get(baseCurrency).getAsDouble(); // Курс базовой валюты относительно USD
        double kztRate = rates.get("KZT").getAsDouble(); // Курс KZT относительно USD

        // Перевод базовой валюты в KZT
        return amount * (kztRate / baseRate);

    }
}