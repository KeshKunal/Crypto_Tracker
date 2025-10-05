package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class ApiService {
    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price?ids=%s&vs_currencies=usd";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();
    
    /**
     * Fetches price for a single coin ID.
     * @param coinId The coin ID (e.g., "bitcoin")
     * @return The current price in USD, or 0.0 if not found
     */
    public static double getPrice(String coinId) {
        if (coinId == null || coinId.trim().isEmpty()) {
            return 0.0;
        }
        
        Map<String, Double> prices = getPrices(List.of(coinId.trim()));
        return prices.getOrDefault(coinId.trim(), 0.0);
    }

    /**
     * Fetches prices for multiple coins in a single API call.
     * @param coinIds List of coin IDs (e.g., ["bitcoin", "ethereum"])
     * @return Map of coin IDs to their current prices
     */
    public static Map<String, Double> getPrices(List<String> coinIds) {
        if (coinIds == null || coinIds.isEmpty()) {
            return Collections.emptyMap();
        }

        try {
            String idsString = String.join(",", coinIds);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(API_URL, idsString)))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() != 200) {
                System.err.println("API returned status code: " + response.statusCode());
                return Collections.emptyMap();
            }

            Type type = new TypeToken<Map<String, Map<String, Double>>>(){}.getType();
            Map<String, Map<String, Double>> fullResponse = gson.fromJson(response.body(), type);

            return fullResponse.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().getOrDefault("usd", 0.0)
                ));

        } catch (Exception e) {
            System.err.println("Error fetching batch prices: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}
