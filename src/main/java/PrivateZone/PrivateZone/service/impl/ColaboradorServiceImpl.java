package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Colaborador;
import PrivateZone.PrivateZone.repository.ColaboradorRepository;
import PrivateZone.PrivateZone.service.ColaboradorService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorServiceImpl(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Colaborador> getColaboradoresPorArea(String area) {
        return colaboradorRepository.findByAreaOrderByIdAsc(area);
    }
}