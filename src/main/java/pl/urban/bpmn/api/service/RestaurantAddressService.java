package pl.urban.bpmn.api.service;

import org.springframework.stereotype.Service;
import pl.urban.bpmn.api.dto.SearchedRestaurantDTO;
import pl.urban.bpmn.api.model.Restaurant;
import pl.urban.bpmn.api.model.RestaurantAddress;
import pl.urban.bpmn.api.repository.RestaurantAddressRepository;
import pl.urban.bpmn.api.repository.RestaurantRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantAddressService {

    private final RestaurantAddressRepository restaurantAddressRepository;
    private final RestaurantRepository restaurantRepository;
    private final GeocodingService geocodingService;


    public RestaurantAddressService(RestaurantAddressRepository restaurantAddressRepository, RestaurantRepository restaurantRepository, GeocodingService geocodingService) {
        this.restaurantAddressRepository = restaurantAddressRepository;
        this.restaurantRepository = restaurantRepository;
        this.geocodingService = geocodingService;
    }

    public RestaurantAddress getRestaurantAddressById(Long id) {
        return restaurantAddressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Restaurant address with id " + id + " not found"));
    }

    public RestaurantAddress addAddress(RestaurantAddress restaurantAddress, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new IllegalArgumentException("Restaurant with id " + restaurantId + " not found"));
        restaurantAddress.setRestaurant(restaurant);
        return restaurantAddressRepository.save(restaurantAddress);
    }

    public List<SearchedRestaurantDTO> searchNearbyRestaurants(String address, double radiusKm)  {
        String formattedAddress = removeCommas(address);
        Map<String, Double> coords = geocodingService.getCoordinates(formattedAddress);
        double latitude = coords.get("lat");
        double longitude = coords.get("lon");

        List<RestaurantAddress> restaurantAddresses = restaurantAddressRepository.findNearbyRestaurants(latitude, longitude, radiusKm);
        return restaurantAddresses.stream()
                .map(restaurantAddress -> convertToDTO(restaurantAddress, latitude, longitude))
                .collect(Collectors.toList());
    }

    private SearchedRestaurantDTO convertToDTO(RestaurantAddress restaurantAddress, double userLatitude, double userLongitude) {
       SearchedRestaurantDTO dto = new SearchedRestaurantDTO();

       dto.setLatitude(restaurantAddress.getLatitude());
       dto.setLongitude(restaurantAddress.getLongitude());
       dto.setRestaurantId(restaurantAddress.getRestaurant().getId());

       double distance = calculateDistance(userLatitude, userLongitude, restaurantAddress.getLatitude(), restaurantAddress.getLongitude());
       dto.setDistance(distance);

       dto.setName(restaurantAddress.getRestaurant().getName());
       dto.setCategory(restaurantAddress.getRestaurant().getCategory());

         return dto;

    }

    private String removeCommas(String address) {
        String addressWithoutCommas = address.replace(",", "");
        return addressWithoutCommas
                .replace("ą", "a")
                .replace("ć", "c")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ń", "n")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ź", "z")
                .replace("ż", "z");

    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;

        return Math.round(distance * 1000.0) / 1000.0;

    }

    public void deleteRestaurantAddress(Long id) {
        restaurantAddressRepository.deleteById(id);
    }

    public RestaurantAddress updateAddress(Long id, RestaurantAddress updatedAddress) {
        RestaurantAddress existingAddress = restaurantAddressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant address with id " + id + " not found"));

        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setFlatNumber(updatedAddress.getFlatNumber());
        existingAddress.setRestaurant(updatedAddress.getRestaurant());
        existingAddress.setZipCode(updatedAddress.getZipCode());

        existingAddress.setLatitude(updatedAddress.getLatitude());
        existingAddress.setLongitude(updatedAddress.getLongitude());

        return restaurantAddressRepository.save(existingAddress);
    }

    public List<RestaurantAddress> getAllRestaurantAddresses() {
        return restaurantAddressRepository.findAll();
    }


}
