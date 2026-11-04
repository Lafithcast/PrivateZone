package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Colaborador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findByAreaOrderByIdAsc(String area);

    // todos los colaboradores con su rol cargado en la misma consulta
    @EntityGraph(attributePaths = "rol")
    List<Colaborador> findAllByOrderByAreaAscNombreAsc();

    // para iniciar sesión: el colaborador con ese correo corporativo, con su rol cargado
    @EntityGraph(attributePaths = "rol")
    Optional<Colaborador> findByCorreo(String correo);
}