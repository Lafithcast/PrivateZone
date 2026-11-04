/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.security;

/**
 *
 * @author home
 */

import PrivateZone.PrivateZone.domain.Colaborador;
import PrivateZone.PrivateZone.repository.ColaboradorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Carga el usuario desde la tabla colaborador: el correo corporativo es el usuario,
 * la contraseña es el hash BCrypt guardado y el rol viene de la tabla rol.
 */
@Service
public class ColaboradorUserDetailsService implements UserDetailsService {

    /** Prefijo de Spring Security para roles: el rol "Administrador" queda como ROLE_Administrador. */
    public static final String PREFIJO_ROL = "ROLE_";

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorUserDetailsService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // solo entran los colaboradores que tienen contraseña y un rol asignado
        Colaborador c = colaboradorRepository.findByCorreo(correo)
                .filter(x -> x.getPassword() != null && !x.getPassword().isBlank() && x.getRol() != null)
                .orElseThrow(() -> new UsernameNotFoundException("No hay un colaborador con acceso: " + correo));

        return User.withUsername(c.getCorreo())
                .password(c.getPassword())
                .authorities(PREFIJO_ROL + c.getRol().getNombre())
                .disabled(!"ACTIVO".equals(c.getEstado()))   // un colaborador INACTIVO no puede entrar
                .build();
    }
}
