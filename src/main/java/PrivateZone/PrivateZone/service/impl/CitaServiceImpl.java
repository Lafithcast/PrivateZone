package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Cita;
import PrivateZone.PrivateZone.domain.Servicio;
import PrivateZone.PrivateZone.repository.CitaRepository;
import PrivateZone.PrivateZone.service.CitaService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    // horario tomado de footer.horario1 (lun a sáb, 8:00 a. m. a 5:00 p. m.); actualizar si el negocio confirma otro
    private static final int APERTURA_MINUTOS = 8 * 60;
    private static final int CIERRE_MINUTOS = 17 * 60;
    private static final int INTERVALO_MINUTOS = 30;
    private static final DayOfWeek DIA_NO_LABORAL = DayOfWeek.SUNDAY;

    private final CitaRepository citaRepository;

    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    @Transactional
    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cita> buscarPorId(Long id) {
        return citaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean especialistaOcupado(Long especialistaId, LocalDate fecha, LocalTime hora, int duracionMinutos) {
        int inicio = hora.toSecondOfDay() / 60;
        int fin = inicio + duracionMinutos;
        for (Cita c : citaRepository.findByEspecialistaIdAndFecha(especialistaId, fecha)) {
            int citaInicio = c.getHora().toSecondOfDay() / 60;
            int citaFin = citaInicio + c.getServicio().getDuracionMinutos();
            if (citaInicio < fin && citaFin > inicio) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<LocalTime> getHorasInicio(Servicio servicio) {
        List<LocalTime> horas = new ArrayList<>();
        for (int m = APERTURA_MINUTOS; m + servicio.getDuracionMinutos() <= CIERRE_MINUTOS; m += INTERVALO_MINUTOS) {
            horas.add(LocalTime.of(m / 60, m % 60));
        }
        return horas;
    }

    @Override
    public boolean esDiaLaboral(LocalDate fecha) {
        return fecha.getDayOfWeek() != DIA_NO_LABORAL;
    }
}
