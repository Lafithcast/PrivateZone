package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/servicios")
public class PortalServicioController {

    private final ServicioService servicioService;

    public PortalServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", servicioService.getServiciosDisponibles());
        return "portal/servicios";
    }
}
