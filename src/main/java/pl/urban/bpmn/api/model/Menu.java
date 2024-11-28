package pl.urban.bpmn.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String ingredients;

    @Column(nullable = false)
    private double price;

    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus;

    @JsonIgnore
    @ManyToMany(mappedBy = "menu")
    private Set<Restaurant> restaurant;


}
