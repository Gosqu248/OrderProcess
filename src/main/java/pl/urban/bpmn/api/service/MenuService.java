package pl.urban.bpmn.api.service;

import org.springframework.stereotype.Service;
import pl.urban.bpmn.api.model.Menu;
import pl.urban.bpmn.api.repository.MenuRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    public List<Menu> getRestaurantMenu(Long restaurantId) {
        List<Menu> menuItems = menuRepository.findByRestaurantId(restaurantId);
        return sortByCategory(menuItems);
    }

    public Menu getMenuItem(Long menuItemId) {
        return menuRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu with id " + menuItemId + " not found"));
    }

    public Menu addMenuItem(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteMenuItem(Long menuItemId) {
        menuRepository.deleteById(menuItemId);
    }

    public Set<String> getRestaurantMenuCategories(Long restaurantId) {
        List<Menu> menuItems = menuRepository.findByRestaurantId(restaurantId);
        return menuItems.stream()
                .map(Menu::getCategory)
                .collect(Collectors.toSet());
    }

    public Menu updateMenuItem(Long menuId, Menu menu) {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu with id " + menuId + " not found"));

        existingMenu.setName(menu.getName());
        existingMenu.setPrice(menu.getPrice());
        existingMenu.setCategory(menu.getCategory());
        existingMenu.setRestaurant(menu.getRestaurant());
        existingMenu.setIngredients(menu.getIngredients());


        return menuRepository.save(menu);
    }

    public List<Menu> sortByCategory(List<Menu> menuItems) {
        Map<String, List<Menu>> sortedMenu = menuItems.stream()
                .collect(Collectors.groupingBy(Menu::getCategory, TreeMap::new, Collectors.toList()));

        return sortedMenu.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
