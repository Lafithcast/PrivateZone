package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Categoria;
import PrivateZone.PrivateZone.repository.CategoriaRepository;
import PrivateZone.PrivateZone.service.CategoriaService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> getCategoriasPorArea(String area) {
        return categoriaRepository.findByAreaOrderByIdAsc(area);
    }
}