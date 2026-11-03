package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Promocion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {

    List<Promocion> findByActivaTrueOrderByOrdenAsc();

    // promociones activas cuyo rango de fechas incluye "hoy" (fecha nula = sin límite)
    @Query("select p from Promocion p where p.activa = true "
            + "and (p.fechaInicio is null or p.fechaInicio <= :hoy) "
            + "and (p.fechaFin is null or p.fechaFin >= :hoy) "
            + "order by p.orden asc")
    List<Promocion> findVigentes(@Param("hoy") LocalDate hoy);
}