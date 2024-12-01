package pl.urban.bpmn.workers;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.urban.bpmn.api.model.Menu;
import pl.urban.bpmn.api.model.Order;
import pl.urban.bpmn.api.model.OrderMenu;
import pl.urban.bpmn.api.model.Restaurant;
import pl.urban.bpmn.api.service.CartService;
import pl.urban.bpmn.api.service.OrderService;
import pl.urban.bpmn.api.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class AcceptOrderWorker {

    private final CartService cartService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;


    @JobWorker(type = "acceptOrder")
    public Map<String, Object> loadRestaurantMenu(final ActivatedJob job) {
        System.out.println("Accepting order");
        var jobResultVariables = job.getVariablesAsMap();

        String  resId = (String) jobResultVariables.get("restaurantId");
        Long restaurantId = Long.parseLong(resId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        double total = cartService.calculateTotal();
        List<Menu> menus = cartService.getMenus();

        Order order = new Order();
        order.setTotalPrice(total);
        order.setRestaurant(restaurant);

        List<OrderMenu> orderMenus = new ArrayList<>();
        for (Menu menu : menus) {
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setMenu(menu);
            orderMenu.setQuantity(1);
            orderMenu.setOrder(order);
            orderMenus.add(orderMenu);
        }
        order.setOrderMenus(orderMenus);

        orderService.createOrder(order);

        return jobResultVariables;
    }

}
