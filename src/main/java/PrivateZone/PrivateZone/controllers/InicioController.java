package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.service.CategoriaService;
import PrivateZone.PrivateZone.service.ColaboradorService;
import PrivateZone.PrivateZone.service.PromocionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final PromocionService promocionService;
    private final CategoriaService categoriaService;
    private final ColaboradorService colaboradorService;

    public InicioController(PromocionService promocionService,
                            CategoriaService categoriaService,
                            ColaboradorService colaboradorService) {
        this.promocionService = promocionService;
        this.categoriaService = categoriaService;
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("promociones", promocionService.getPromocionesActivas());
        model.addAttribute("categoriasCafe", categoriaService.getCategoriasPorArea("CAFE"));
        model.addAttribute("categoriasSalon", categoriaService.getCategoriasPorArea("SALON"));
        model.addAttribute("baristas", colaboradorService.getColaboradoresPorArea("CAFE"));
        model.addAttribute("especialistas", colaboradorService.getColaboradoresPorArea("SALON"));
        return "index";
    }
}