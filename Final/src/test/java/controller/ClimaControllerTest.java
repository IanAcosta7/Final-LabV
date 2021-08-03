package controller;

import ar.edu.utn.mdp.Final.controller.ClimaController;
import ar.edu.utn.mdp.Final.dto.ClimaDTO;
import ar.edu.utn.mdp.Final.model.Clima;
import ar.edu.utn.mdp.Final.service.ClimaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ClimaControllerTest {

    private ClimaService climaServiceMock;
    private ClimaController climaController;

    @BeforeAll
    public void setup() {
        climaServiceMock = mock(ClimaService.class);
        climaController = new ClimaController(climaServiceMock);
    }

    @Test
    public void testGetClima() {
        Clima clima = new Clima(1, "test", "test", 1, "test", 1, "test", "test");
        ClimaDTO climaDTO = new ClimaDTO("test", "test", null, "test");
        Mockito.when(climaServiceMock.getClimaActual(1, 1)).thenReturn(clima);

        ResponseEntity<ClimaDTO> result = climaController.getClima(1, 1);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(climaDTO.getTimezone(), result.getBody().getTimezone());
        Assertions.assertEquals(climaDTO.getCityName(), result.getBody().getCityName());
        Assertions.assertEquals(climaDTO.getHora(), result.getBody().getHora());
    }

    @Test
    public void testGetClima_NullResponse() {
        List<String> alerts = new ArrayList<>();
        Clima clima = new Clima(0, null, null, 0, null, 0, null, null);
        Mockito.when(climaServiceMock.getClimaActual(1, 1)).thenReturn(clima);

        ResponseEntity<ClimaDTO> result = climaController.getClima(1, 1);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(1, result.getBody().getAlerts().size());
    }

}
