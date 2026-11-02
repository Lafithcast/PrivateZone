/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mafer
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    public List<Producto> findAllByOrderByNombreAsc();

    public List<Producto> findByCategoriaIdOrderByNombreAsc(Long categoriaId);
}
