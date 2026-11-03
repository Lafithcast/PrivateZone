/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.service.CategoriaService;
import PrivateZone.PrivateZone.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author mafer
 */
@Controller
@RequestMapping("/cafe")
public class CafeController {

    private final CategoriaService categoriaService;
    private final ProductoService productoService;

    public CafeController(CategoriaService categoriaService,
            ProductoService productoService) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
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
}
