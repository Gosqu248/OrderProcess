package pl.urban.bpmn.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.urban.bpmn.api.service.RestaurantAddressService;

import java.util.Map;

@Component
@AllArgsConstructor
public class SearchAddressWorker {

    private RestaurantAddressService restaurantAddressService;

    @JobWorker(type = "findNearbyRestaurants")
    public Map<String, Object> findNearbyRestaurants(final JobClient client, final ActivatedJob job) {
        var jobResultVariables = job.getVariablesAsMap();
        double radiusKm = 5.0;

        String address = (String) jobResultVariables.get("address");
        if (address == null || address.trim().isEmpty()) {
            jobResultVariables.put("isRestaurant", false);
            return jobResultVariables;
        }

        var restaurants = restaurantAddressService.searchNearbyRestaurants(address, radiusKm);

        if (!restaurants.isEmpty()) {
            jobResultVariables.put("restaurants", restaurants);
            jobResultVariables.put("isRestaurant", true);
        } else {
            jobResultVariables.put("isRestaurant", false);
        }

        return jobResultVariables;
    }

}