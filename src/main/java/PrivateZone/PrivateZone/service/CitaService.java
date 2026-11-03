package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Cita;
import PrivateZone.PrivateZone.domain.Servicio;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaService {

    Cita guardar(Cita cita);

    Optional<Cita> buscarPorId(Long id);

    // true si otra cita del especialista, de cualquier estado, se traslapa con [hora, hora + duración)
    boolean especialistaOcupado(Long especialistaId, LocalDate fecha, LocalTime hora, int duracionMinutos);

    // horas de inicio válidas para el servicio: cada 30 minutos y terminando antes del cierre
    List<LocalTime> getHorasInicio(Servicio servicio);

    // true si la fecha cae en un día de atención
    boolean esDiaLaboral(LocalDate fecha);
}
