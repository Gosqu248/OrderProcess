package pl.urban.bpmn.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.urban.bpmn.api.model.OrderMenu;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
