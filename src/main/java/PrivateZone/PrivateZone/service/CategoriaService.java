package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Categoria;
import java.util.List;

public interface CategoriaService {

    List<Categoria> getCategoriasPorArea(String area);
}