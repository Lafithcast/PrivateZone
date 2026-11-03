/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.domain.ReservaMesa;
import PrivateZone.PrivateZone.service.CategoriaService;
import PrivateZone.PrivateZone.service.ProductoService;
import PrivateZone.PrivateZone.service.ReservaMesaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mafer
 */
@Controller
@RequestMapping("/cafe")
public class CafeController {

    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final ReservaMesaService reservaMesaService;

    public CafeController(CategoriaService categoriaService,
            ProductoService productoService,
            ReservaMesaService reservaMesaService) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.reservaMesaService = reservaMesaService;
    }

    @GetMapping
    public String inicio(Model model) {
        model.addAttribute("categorias",
                categoriaService.getCategoriasPorArea("CAFE"));
        return "cafe/inicio";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("categorias",
                categoriaService.getCategoriasPorArea("CAFE"));
        model.addAttribute("productos", productoService.getProductos());
        return "cafe/menu";
    }

    @GetMapping("/reservar")
    public String reservar(Model model) {
        model.addAttribute("reserva", new ReservaMesa());
        return "cafe/reservar";
    }

    @PostMapping("/reservar")
    public String guardarReserva(@ModelAttribute("reserva") ReservaMesa reserva,
            RedirectAttributes redirect) {
        reservaMesaService.guardar(reserva);
        redirect.addFlashAttribute("reservaOk", true);
        return "redirect:/cafe/reservar";
    }
}
