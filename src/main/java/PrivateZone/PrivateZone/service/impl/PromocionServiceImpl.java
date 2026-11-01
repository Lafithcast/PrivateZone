package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Promocion;
import PrivateZone.PrivateZone.repository.PromocionRepository;
import PrivateZone.PrivateZone.service.PromocionService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromocionServiceImpl implements PromocionService {

    private final PromocionRepository promocionRepository;

    public PromocionServiceImpl(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Promocion> getPromocionesActivas() {
        return promocionRepository.findByActivaTrueOrderByOrdenAsc();
    }
}