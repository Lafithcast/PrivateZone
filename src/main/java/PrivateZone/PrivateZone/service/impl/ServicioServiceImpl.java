package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Servicio;
import PrivateZone.PrivateZone.repository.ServicioRepository;
import PrivateZone.PrivateZone.service.ServicioService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> getServiciosDisponibles() {
        return servicioRepository.findByDisponibleTrueOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> getServiciosPorCategoria(Long categoriaId) {
        return servicioRepository.findByCategoriaIdAndDisponibleTrueOrderByNombreAsc(categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> getServiciosDisponiblesConEspecialistas() {
        return servicioRepository.findDisponiblesConEspecialistas();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> buscarPorId(Long id) {
        return servicioRepository.findWithEspecialistasById(id);
    }
}
