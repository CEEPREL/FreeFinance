package com.ceeprel.service;

import com.ceeprel.dto.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LocationService {

    @Value("${geolocation.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LocationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Location getLocationFromIp(String ipAddress) {
        String apiUrl = buildUrl(ipAddress);

        try {
            String response = restTemplate.getForObject(apiUrl, String.class);
            return objectMapper.readValue(response, Location.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse location data", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch location data", e);
        }
    }

    private String buildUrl(String ipAddress) {
        return UriComponentsBuilder.fromHttpUrl("https://api.ipgeolocation.io/ipgeo")
                .queryParam("apiKey", apiKey)
                .queryParam("ip", ipAddress)
                .toUriString();
    }
}
