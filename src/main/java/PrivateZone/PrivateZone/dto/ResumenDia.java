/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.dto;

/**
 *
 * @author home
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

// datos de la pantalla "Resumen del día" del portal, ya calculados para mostrarse
@Data
public class ResumenDia {

    private String fechaTexto;
    private String diaSemana;

    private int totalCitas;
    private int pendientes;
    private int completadas;
    private int canceladas;

    // solo servicios del Salón completados: el proyecto no registra ventas del Café
    private long ventasSalon;

    private int promocionesActivas;
    private int promocionesProgramadas;
    private int promocionesVencenPronto;

    private int clientesAtendidos;

    // null cuando el mismo día de la semana anterior no tuvo clientes atendidos con qué comparar
    private Integer variacionClientes;

    private List<FilaCita> citas = new ArrayList<>();

    // una fila de la tabla: una cita del Salón o una reserva de mesa del Café
    @Data
    public static class FilaCita {

        private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

        private LocalTime hora;
        private String cliente;
        private String servicio;
        private String area;
        private String especialista;
        private String estado;
        private String estadoClase;

        public String getHoraTexto() {
            return hora.format(FORMATO_HORA);
        }
    }
}
