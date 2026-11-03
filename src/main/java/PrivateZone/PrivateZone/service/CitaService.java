package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Cita;
import java.util.Optional;

public interface CitaService {

    Cita guardar(Cita cita);

    Optional<Cita> buscarPorId(Long id);
}
