/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.service;

/**
 *
 * @author home
 */

import PrivateZone.PrivateZone.dto.ResumenDia;
import java.time.LocalDate;

public interface ResumenDiaService {

    /** Citas, reservas, promociones y clientes atendidos de la fecha dada, listos para mostrarse. */
    ResumenDia getResumen(LocalDate fecha);
}
