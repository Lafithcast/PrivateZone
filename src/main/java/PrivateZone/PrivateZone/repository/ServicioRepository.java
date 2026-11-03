package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Servicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByDisponibleTrueOrderByNombreAsc();

    List<Servicio> findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(Long categoriaId);

    // carga los especialistas junto con el servicio (paso 2 de la reserva)
    @EntityGraph(attributePaths = "especialistas")
    Optional<Servicio> findWithEspecialistasById(Long id);
}
