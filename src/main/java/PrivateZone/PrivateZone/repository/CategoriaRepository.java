package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByAreaOrderByIdAsc(String area);
}