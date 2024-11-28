package pl.urban.bpmn.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.urban.bpmn.api.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
