package pl.urban.bpmn.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeocodingResponse {
    private String lat;
    private String lon;
}
