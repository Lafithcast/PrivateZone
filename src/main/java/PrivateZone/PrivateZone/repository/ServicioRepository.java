package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Servicio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByDisponibleTrueOrderByNombreAsc();

    List<Servicio> findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(Long categoriaId);

    // servicios disponibles con categoría y especialistas cargados (paso 1 de agendar)
    @EntityGraph(attributePaths = {"categoria", "especialistas"})
    @Query("select distinct s from Servicio s where s.disponible = true order by s.categoria.id asc, s.nombre asc")
    List<Servicio> findDisponiblesConEspecialistas();

    // carga los especialistas junto con el servicio (paso 2 de la reserva)
    @EntityGraph(attributePaths = "especialistas")
    Optional<Servicio> findWithEspecialistasById(Long id);
}
