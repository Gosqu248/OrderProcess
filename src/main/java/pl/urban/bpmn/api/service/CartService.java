package pl.urban.bpmn.api.service;

import org.springframework.stereotype.Service;
import pl.urban.bpmn.api.model.Menu;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private List<Menu> cart;

    public CartService() {
        this.cart = new ArrayList<>();
    }

    public void addMenu(Menu menu) {
        cart.add(menu);
    }

    public void removeMenu(Menu menu) {
        cart.remove(menu);
    }

    public void clearCart() {
        cart.clear();
    }

    public List<Menu> getMenus() {
        return new ArrayList<>(cart);
    }
}
