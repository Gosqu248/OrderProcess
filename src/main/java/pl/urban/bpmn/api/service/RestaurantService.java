package pl.urban.bpmn.api.service;

import org.springframework.stereotype.Service;
import pl.urban.bpmn.api.model.Restaurant;
import pl.urban.bpmn.api.repository.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Restaurant with id " + id + " not found"));
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant with id " + id + " not found"));

        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setRestaurantAddress(updatedRestaurant.getRestaurantAddress());
        existingRestaurant.setCategory(updatedRestaurant.getCategory());
        existingRestaurant.setLogoUrl(updatedRestaurant.getLogoUrl());
        existingRestaurant.setImageUrl(updatedRestaurant.getImageUrl());

        return restaurantRepository.save(existingRestaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
