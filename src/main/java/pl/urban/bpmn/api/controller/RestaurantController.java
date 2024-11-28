package pl.urban.bpmn.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.urban.bpmn.api.model.Restaurant;
import pl.urban.bpmn.api.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/add")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant =  restaurantService.addRestaurant(restaurant);
        return ResponseEntity.ok(savedRestaurant);
    }

    @PutMapping("/update")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestParam Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant =  restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }


}
