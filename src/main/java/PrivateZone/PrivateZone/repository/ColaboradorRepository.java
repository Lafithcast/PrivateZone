package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Colaborador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    List<Colaborador> findByAreaOrderByIdAsc(String area);
}