package pl.urban.bpmn.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.urban.bpmn.api.model.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByRestaurantId(Long restaurantId);
}
