package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // citas de un especialista en una fecha, con su servicio cargado para conocer la duración
    @EntityGraph(attributePaths = "servicio")
    List<Cita> findByEspecialistaIdAndFecha(Long especialistaId, LocalDate fecha);

    // todas las citas de una fecha en orden de hora, con servicio y especialista cargados (Resumen del día)
    @EntityGraph(attributePaths = {"servicio", "especialista"})
    List<Cita> findByFechaOrderByHoraAsc(LocalDate fecha);
}
