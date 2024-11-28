package pl.urban.bpmn.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchedRestaurantDTO {

    private Long restaurantId;
    private String name;
    private String category;
    private double latitude;
    private double longitude;
    private double distance;

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", distance=" + distance ;
    }
}
