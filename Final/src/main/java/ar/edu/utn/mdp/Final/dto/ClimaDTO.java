package ar.edu.utn.mdp.Final.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClimaDTO {
    private String hora;
    private String timezone;
    private List<String> alerts;
    private String cityName;
}
