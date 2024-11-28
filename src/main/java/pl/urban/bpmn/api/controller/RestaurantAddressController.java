package pl.urban.bpmn.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.urban.bpmn.api.dto.SearchedRestaurantDTO;
import pl.urban.bpmn.api.model.RestaurantAddress;
import pl.urban.bpmn.api.service.RestaurantAddressService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantAddress")
public class RestaurantAddressController {

    private final RestaurantAddressService restaurantAddressService;

    public RestaurantAddressController(RestaurantAddressService restaurantAddressService) {
        this.restaurantAddressService = restaurantAddressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantAddress> getRestaurantAddress(@PathVariable Long id) {
        RestaurantAddress restaurantAddress = restaurantAddressService.getRestaurantAddressById(id);
        return ResponseEntity.ok(restaurantAddress);
    }

    @PostMapping("/add")
    public ResponseEntity<RestaurantAddress> addRestaurantAddress(@RequestParam Long restaurantId, @RequestBody RestaurantAddress restaurantAddress) {
        RestaurantAddress savedRestaurantAddress =  restaurantAddressService.addAddress(restaurantAddress, restaurantId);
        return ResponseEntity.ok(savedRestaurantAddress);
    }

    @GetMapping("/searchRestaurant")
    public ResponseEntity<List<SearchedRestaurantDTO>> searchRestaurants(
            @RequestParam String address,
            @RequestParam(defaultValue = "6") double radius) {
        List<SearchedRestaurantDTO> results = restaurantAddressService.searchNearbyRestaurants(address, radius);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/update")
    public ResponseEntity<RestaurantAddress> updateRestaurantAddress(@RequestParam Long id, @RequestBody RestaurantAddress restaurantAddress) {
        RestaurantAddress updatedRestaurantAddress =  restaurantAddressService.updateAddress(id, restaurantAddress);
        return ResponseEntity.ok(updatedRestaurantAddress);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurantAddress(@PathVariable Long id) {
        restaurantAddressService.deleteRestaurantAddress(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantAddress>> getAllRestaurantAddresses() {
        return ResponseEntity.ok(restaurantAddressService.getAllRestaurantAddresses());
    }

}

