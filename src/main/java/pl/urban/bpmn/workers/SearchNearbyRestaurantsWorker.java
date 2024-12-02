package pl.urban.bpmn.workers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import pl.urban.bpmn.api.dto.SearchedRestaurantDTO;
import pl.urban.bpmn.api.service.RestaurantAddressService;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SearchNearbyRestaurantsWorker {

    private RestaurantAddressService restaurantAddressService;

    @JobWorker(type = "searchNearbyRestaurants")
    public Map<String, Object> searchNearbyRestaurants(JobClient client, final ActivatedJob job) {
        var jobResultVariables = job.getVariablesAsMap();

        String address = (String) jobResultVariables.get("address");
        System.out.println("Searching for nearby restaurants for address: " + address);

        List<SearchedRestaurantDTO> restaurants = restaurantAddressService.searchNearbyRestaurants(address, 5.0);

        if (restaurants.isEmpty()) {

            client.newThrowErrorCommand(job.getKey())
                        .errorCode("NO_RESTAURANTS_FOUND")
                        .send()
                        .join();
            System.out.println("Restaurants not found ");
            System.out.println("Completion of the process!");
            return jobResultVariables;

        }

        jobResultVariables.put("restaurants", restaurants);
        restaurants.forEach(System.out::println);

        return jobResultVariables;
    }

}
