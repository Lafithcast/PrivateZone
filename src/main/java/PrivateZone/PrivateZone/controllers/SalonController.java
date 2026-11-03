package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.domain.Categoria;
import PrivateZone.PrivateZone.domain.Servicio;
import PrivateZone.PrivateZone.service.CategoriaService;
import PrivateZone.PrivateZone.service.ServicioService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salon")
public class SalonController {

    private final CategoriaService categoriaService;
    private final ServicioService servicioService;

    public SalonController(CategoriaService categoriaService, ServicioService servicioService) {
        this.categoriaService = categoriaService;
        this.servicioService = servicioService;
    }

    @GetMapping
    public String inicio(Model model) {
        List<Categoria> categorias = categoriaService.getCategoriasPorArea("SALON");

        // servicios disponibles por categoría (id), en el orden de las categorías; sin las categorías vacías
        Map<Long, List<Servicio>> serviciosPorCategoria = new LinkedHashMap<>();
        for (Categoria c : categorias) {
            List<Servicio> servicios = servicioService.getServiciosPorCategoria(c.getId());
            if (!servicios.isEmpty()) {
                serviciosPorCategoria.put(c.getId(), servicios);
            }
        }

        model.addAttribute("categorias", categorias);
        model.addAttribute("serviciosPorCategoria", serviciosPorCategoria);
        return "salon/inicio";
    }
}
