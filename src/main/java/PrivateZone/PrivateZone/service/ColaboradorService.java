package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Colaborador;
import java.util.List;

public interface ColaboradorService {

    List<Colaborador> getColaboradoresPorArea(String area);
}