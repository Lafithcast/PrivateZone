package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Colaborador;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findByAreaOrderByIdAsc(String area);

    // todos los colaboradores con su rol cargado en la misma consulta
    @EntityGraph(attributePaths = "rol")
    List<Colaborador> findAllByOrderByAreaAscNombreAsc();
}