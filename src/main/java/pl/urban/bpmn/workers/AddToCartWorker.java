package pl.urban.bpmn.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.urban.bpmn.api.model.Menu;
import pl.urban.bpmn.api.service.CartService;
import pl.urban.bpmn.api.service.MenuService;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class AddToCartWorker {

    private final CartService cartService;
    private final MenuService menuService;

    @JobWorker(type = "addToCart")
    public Map<String, Object> loadRestaurantMenu(final ActivatedJob job) {
        var jobResultVariables = job.getVariablesAsMap();

        String id = (String) jobResultVariables.get("menuId");
        System.out.println("Adding to cart menu with id " + id);

        String moreProduct = (String) jobResultVariables.get("moreProduct");


        jobResultVariables.put("addMore", moreProduct);

        Long menuId = Long.parseLong(id);
        Menu menu = menuService.getMenuItem(menuId);

        cartService.addMenu(menu);

        List<Menu> menuItems = cartService.getMenus();
        System.out.println("Cart items:");
        menuItems.forEach(System.out::println);


        return jobResultVariables;

    }


}
