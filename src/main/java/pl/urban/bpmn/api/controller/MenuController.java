package pl.urban.bpmn.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.urban.bpmn.api.model.Menu;
import pl.urban.bpmn.api.service.MenuService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/getRestaurantMenu")
    public ResponseEntity<List<Menu>> getMenuByRestaurantId(@RequestParam Long restaurantId) {
        try {
            List<Menu> menus = menuService.getRestaurantMenu(restaurantId);
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Menu> addMenuItem(@RequestBody Menu menu) {
        try {
            Menu newMenu = menuService.addMenuItem(menu);
            return ResponseEntity.ok(newMenu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/menuCategories")
    public Set<String> getMenuCategories(@RequestParam Long restaurantId) {
        return menuService.getRestaurantMenuCategories(restaurantId);
    }

    @PutMapping("/update")
    public ResponseEntity<Menu> updateMenuItem(@RequestParam Long menuId, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenuItem(menuId, menu);
            return ResponseEntity.ok(updatedMenu);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteMenuItem(@RequestParam Long menuId) {
        try {
            menuService.deleteMenuItem(menuId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
