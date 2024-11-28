package pl.urban.bpmn.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String logoUrl;

    private String imageUrl;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "restaurant_menu",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @JsonIgnore
    private Set<Menu> menu = new HashSet<>();

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private RestaurantAddress restaurantAddress;
}
