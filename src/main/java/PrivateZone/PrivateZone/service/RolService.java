package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {

    List<Rol> getRoles();

    Optional<Rol> buscarPorId(Long id);
}
