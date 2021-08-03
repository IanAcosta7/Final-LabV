package ar.edu.utn.mdp.Final.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClimaAPI {

    private String countryCode;
    private Double lon;
    private String timezone;
    private Double lat;
    private List<String> alerts;
    private String cityName;
    private String stateCode;

}
