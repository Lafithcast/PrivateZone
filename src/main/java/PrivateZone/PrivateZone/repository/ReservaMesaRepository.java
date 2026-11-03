/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package PrivateZone.PrivateZone.repository;

import PrivateZone.PrivateZone.domain.ReservaMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mafer
 */
@Repository
public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Long> {
}