/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.ReservaMesa;
import PrivateZone.PrivateZone.repository.ReservaMesaRepository;
import PrivateZone.PrivateZone.service.ReservaMesaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mafer
 */
@Service
public class ReservaMesaServiceImpl implements ReservaMesaService {

    private final ReservaMesaRepository reservaMesaRepository;

    public ReservaMesaServiceImpl(ReservaMesaRepository reservaMesaRepository) {
        this.reservaMesaRepository = reservaMesaRepository;
    }

    @Override
    @Transactional
    public void guardar(ReservaMesa reserva) {
        reservaMesaRepository.save(reserva);
    }
}