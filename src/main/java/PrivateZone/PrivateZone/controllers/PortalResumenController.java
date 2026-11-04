/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.controllers;

/**
 *
 * @author home
 */

import PrivateZone.PrivateZone.service.ResumenDiaService;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/resumen")
public class PortalResumenController {

    private final ResumenDiaService resumenDiaService;

    public PortalResumenController(ResumenDiaService resumenDiaService) {
        this.resumenDiaService = resumenDiaService;
    }

    @GetMapping
    public String ver(Model model) {
        model.addAttribute("resumen", resumenDiaService.getResumen(LocalDate.now()));
        return "portal/resumen";
    }
}
