package pl.urban.bpmn.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.urban.bpmn.api.request.GeocodingResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Double> getCoordinates(String address) {
        String url = String.format("https://nominatim.openstreetmap.org/search?format=json&q=%s",
                URLEncoder.encode(address, StandardCharsets.UTF_8));
        ResponseEntity<GeocodingResponse[]> response = restTemplate.getForEntity(url, GeocodingResponse[].class);


            GeocodingResponse location = response.getBody()[0];
            Map<String, Double> coordinates = new HashMap<>();
            coordinates.put("lat", Double.parseDouble(location.getLat()));
            coordinates.put("lon", Double.parseDouble(location.getLon()));
            return coordinates;

    }

}
