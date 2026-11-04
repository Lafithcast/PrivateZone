/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.security;

/**
 *
 * @author home
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/portal/**").authenticated()   // el portal exige iniciar sesión
                .anyRequest().permitAll())                       // el sitio público queda como estaba
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("correo")                     // el campo del formulario es el correo
                .successHandler(alIniciarSesion())
                .failureUrl("/login?error")
                .permitAll())
            .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * El rol elegido en el formulario tiene que ser el rol asignado al colaborador.
     * Si no coincide se cierra la sesión recién creada y se vuelve al login.
     */
    private AuthenticationSuccessHandler alIniciarSesion() {
        SavedRequestAwareAuthenticationSuccessHandler normal = new SavedRequestAwareAuthenticationSuccessHandler();
        normal.setDefaultTargetUrl("/portal/resumen");
        return (request, response, authentication) -> {
            String elegido = request.getParameter("rol");
            boolean coincide = authentication.getAuthorities().contains(
                    new SimpleGrantedAuthority(ColaboradorUserDetailsService.PREFIJO_ROL + elegido));
            if (!coincide) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                response.sendRedirect(request.getContextPath() + "/login?rolIncorrecto");
                return;
            }
            normal.onAuthenticationSuccess(request, response, authentication);
        };
    }
}
