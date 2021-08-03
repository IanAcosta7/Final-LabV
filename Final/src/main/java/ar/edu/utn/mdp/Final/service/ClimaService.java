package ar.edu.utn.mdp.Final.service;

import ar.edu.utn.mdp.Final.model.Clima;
import ar.edu.utn.mdp.Final.model.ClimaAPI;
import ar.edu.utn.mdp.Final.repository.ClimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class ClimaService {

    private ClimaRepository climaRepository;

    @Autowired
    public ClimaService(ClimaRepository climaRepository) {
        this.climaRepository = climaRepository;
    }

    public Clima getClimaActual(Integer lat, Integer lon) {
        Clima climaActual = null;
        Clima climaSaved = climaRepository.findByLatAndLon(lat, lon);

        if (climaSaved != null) {
            climaActual = climaSaved;
        } else {
            ClimaAPI climaAPI = getClimaAPI();

            if (climaAPI != null) {
                climaActual = new Clima();
                climaActual.setHora(Time.valueOf(LocalTime.now()).toString());
                climaActual.setCityName(climaAPI.getCityName());
                climaActual.setTimezone(climaAPI.getTimezone());
                climaActual.setCityName(climaAPI.getCityName());
                climaActual.setStateCode(climaAPI.getStateCode());
                climaActual.setLat((int)Math.round(climaAPI.getLat()));
                climaActual.setLon((int)Math.round(climaAPI.getLon()));
                climaActual = climaRepository.save(climaActual);
            }
        }

        return climaActual;
    }

    private ClimaAPI getClimaAPI() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherbit-v1-mashape.p.rapidapi.com/alerts?lat=38.5&lon=-78.5"))
                .header("x-rapidapi-key", "92b608d237mshef3f2f5cf61f409p1c3848jsn568653fe99a7")
                .header("x-rapidapi-host", "weatherbit-v1-mashape.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            GsonJsonParser gsonParser = new GsonJsonParser();

            Map<String, Object> parsed = gsonParser.parseMap(response.body());

            ClimaAPI climaAPI = new ClimaAPI(
                    (String)parsed.get("country_code"),
                    (Double)parsed.get("lon"),
                    (String)parsed.get("timezone"),
                    (Double)parsed.get("lat"),
                    (List<String>)parsed.get("alerts"),
                    (String)parsed.get("city_name"),
                    (String)parsed.get("state_code")
            );

            // parseado
            return climaAPI;
        } catch (Exception exception) {
            return null;
        }
    }

}
