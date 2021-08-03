package ar.edu.utn.mdp.Final.repository;

import ar.edu.utn.mdp.Final.model.Clima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimaRepository extends JpaRepository<Clima, Integer> {

    Clima findByLatAndLon(Integer lat, Integer lon);

}
