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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mafer
 */
@Controller
@RequestMapping("/portal/productos")
public class PortalProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public PortalProductoController(ProductoService productoService,
            CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long categoriaId,
            Model model) {
        model.addAttribute("categorias",
                categoriaService.getCategoriasPorArea("CAFE"));
        model.addAttribute("productos", categoriaId == null
                ? productoService.getProductos()
                : productoService.getProductosPorCategoria(categoriaId));
        model.addAttribute("categoriaSel", categoriaId);
        return "portal/productos";
    }

    @PostMapping("/precio")
    public String cambiarPrecio(@RequestParam Long id,
            @RequestParam Integer precio) {
        productoService.actualizarPrecio(id, precio);
        return "redirect:/portal/productos";
    }
}