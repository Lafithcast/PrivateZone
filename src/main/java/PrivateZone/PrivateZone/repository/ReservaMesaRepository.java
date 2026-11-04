/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.ReservaMesa;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mafer
 */
@Repository
public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Long> {

    // reservas de una fecha en orden de hora (Resumen del día)
    List<ReservaMesa> findByFechaOrderByHoraAsc(LocalDate fecha);
}