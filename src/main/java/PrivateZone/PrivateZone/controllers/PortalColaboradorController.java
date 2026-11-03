package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.service.ColaboradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal/colaboradores")
public class PortalColaboradorController {

    private final ColaboradorService colaboradorService;

    public PortalColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("colaboradores", colaboradorService.getColaboradores());
        return "portal/colaboradores";
    }
}
