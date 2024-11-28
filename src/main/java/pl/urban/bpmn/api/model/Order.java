package pl.urban.bpmn.api.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderMenu> orderMenus;

    private ZonedDateTime orderDate;
    @PrePersist
    protected void onCreate() {
        orderDate = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
    }
}