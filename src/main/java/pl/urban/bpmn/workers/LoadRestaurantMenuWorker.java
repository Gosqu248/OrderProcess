package pl.urban.bpmn.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.urban.bpmn.api.model.Menu;
import pl.urban.bpmn.api.service.MenuService;
import pl.urban.bpmn.api.service.RestaurantService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class LoadRestaurantMenuWorker {

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @JobWorker(type = "loadRestaurantMenu")
    public Map<String, Object> loadRestaurantMenu(final ActivatedJob job) {

        var jobResultVariables = job.getVariablesAsMap();
        String id = (String) jobResultVariables.get("restaurantId");
        System.out.println("Loading menu for restaurant with id: " + id);


        Long restaurantId = Long.parseLong(id);

        List<Menu> menuItems = menuService.getRestaurantMenu(restaurantId);
        jobResultVariables.put("menuItems", menuItems);

        menuItems.forEach(System.out::println);


        return jobResultVariables;

    }



}
