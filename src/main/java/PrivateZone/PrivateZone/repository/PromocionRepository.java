package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Promocion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {

    List<Promocion> findByActivaTrueOrderByOrdenAsc();
}