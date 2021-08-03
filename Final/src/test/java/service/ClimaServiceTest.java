package service;

import ar.edu.utn.mdp.Final.model.Clima;
import ar.edu.utn.mdp.Final.repository.ClimaRepository;
import ar.edu.utn.mdp.Final.service.ClimaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class ClimaServiceTest {

    private ClimaRepository climaRepositoryMock;
    private ClimaService climaService;

    @BeforeAll
    public void setup() {
        climaRepositoryMock = mock(ClimaRepository.class);

        climaService = new ClimaService(climaRepositoryMock);
    }

    @Test
    public void testGetClimaActual_FirstSave() {
        Clima clima = new Clima(1, "test", "test", 1, "test", 1, "test", "test");
        Mockito.when(climaRepositoryMock.findByLatAndLon(1, 1)).thenReturn(null);
        Mockito.when(climaRepositoryMock.save(clima)).thenReturn(clima);

        Clima result = climaService.getClimaActual(1, 1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(clima, result);
    }

    @Test
    public void testGetClimaActual_Update() {
        Clima clima = new Clima(1, "test", "test", 1, "test", 1, "test", "test");
        Mockito.when(climaRepositoryMock.findByLatAndLon(1, 1)).thenReturn(clima);
        Mockito.when(climaRepositoryMock.save(clima)).thenReturn(clima);

        Clima result = climaService.getClimaActual(1, 1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(clima, result);
    }

    @Test
    public void testGetClimaActual_BothResponsesNull() {
        Clima clima = new Clima(1, "test", "test", 1, "test", 1, "test", "test");
        Mockito.when(climaRepositoryMock.findByLatAndLon(1, 1)).thenReturn(null);
        Mockito.when(climaRepositoryMock.save(clima)).thenReturn(null);

        Clima result = climaService.getClimaActual(1, 1);

        Assertions.assertNull(result);
    }

}
