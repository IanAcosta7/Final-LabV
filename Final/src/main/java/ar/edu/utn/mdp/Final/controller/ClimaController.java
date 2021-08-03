package ar.edu.utn.mdp.Final.controller;

import ar.edu.utn.mdp.Final.dto.ClimaDTO;
import ar.edu.utn.mdp.Final.model.Clima;
import ar.edu.utn.mdp.Final.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class ClimaController {

    private ClimaService climaService;

    @Autowired
    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @GetMapping("/ClimaNow")
    public ResponseEntity<ClimaDTO> getClima(
            @RequestParam(name = "lat", defaultValue = "0") Integer lat,
            @RequestParam(name = "lon", defaultValue = "0") Integer lon
    ) {
        Clima clima = climaService.getClimaActual(lat, lon);
        List<String> alerts = null;

        if (clima == null) {
            // Default response
            alerts = new ArrayList<>();

            alerts.add("No hay respuestas actualmente.");

            clima = new Clima(0, null, null, 0, null, 0, null, null);
        }

        return ResponseEntity.ok(mapClimaToClimaDTO(clima, alerts));
    }

    private ClimaDTO mapClimaToClimaDTO(Clima climaActual, List<String> alerts) {
        return new ClimaDTO(climaActual.getHora(), climaActual.getTimezone(), alerts, climaActual.getCityName());
    }

}
