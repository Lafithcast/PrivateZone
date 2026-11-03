package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Cita;
import PrivateZone.PrivateZone.repository.CitaRepository;
import PrivateZone.PrivateZone.service.CitaService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

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
}
