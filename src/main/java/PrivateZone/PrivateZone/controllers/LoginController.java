/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.controllers;

/**
 *
 * @author home
 */

import PrivateZone.PrivateZone.service.RolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final RolService rolService;

    public LoginController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        // los roles del selector salen de la tabla rol
        model.addAttribute("roles", rolService.getRoles());
        return "login";
    }
}
