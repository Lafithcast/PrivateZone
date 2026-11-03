package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Servicio;
import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<Servicio> getServiciosDisponibles();

    List<Servicio> getServiciosPorCategoria(Long categoriaId);

    // ordenados por categoría y nombre, con categoría y especialistas ya cargados
    List<Servicio> getServiciosDisponiblesConEspecialistas();

    // incluye los especialistas del servicio ya cargados
    Optional<Servicio> buscarPorId(Long id);
}
