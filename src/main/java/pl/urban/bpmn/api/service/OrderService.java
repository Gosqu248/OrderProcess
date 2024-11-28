package pl.urban.bpmn.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.urban.bpmn.api.model.Menu;
import pl.urban.bpmn.api.model.Order;
import pl.urban.bpmn.api.model.OrderMenu;
import pl.urban.bpmn.api.model.Restaurant;
import pl.urban.bpmn.api.repository.MenuRepository;
import pl.urban.bpmn.api.repository.OrderRepository;
import pl.urban.bpmn.api.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Transactional
    public Order createOrder(Order orderRequest) {
        try {
            Order order = new Order();

            order.setTotalPrice(orderRequest.getTotalPrice());

            Restaurant restaurant = restaurantRepository.findById(orderRequest.getRestaurant().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
            order.setRestaurant(restaurant);



            List<OrderMenu> orderMenus = new ArrayList<>();
            if (orderRequest.getOrderMenus() != null && !orderRequest.getOrderMenus().isEmpty()) {
                for (OrderMenu requestOrderMenu : orderRequest.getOrderMenus()) {
                    OrderMenu orderMenu = new OrderMenu();

                    orderMenu.setQuantity(requestOrderMenu.getQuantity());

                    Menu menu = menuRepository.findById(requestOrderMenu.getMenu().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
                    orderMenu.setMenu(menu);


                    orderMenu.setOrder(order);
                    orderMenus.add(orderMenu);
                }
            }
            order.setOrderMenus(orderMenus);

            return orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
            throw new RuntimeException("Error creating order: " + e.getMessage(), e);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


}
