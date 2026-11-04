/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PrivateZone.PrivateZone.service.impl;

/**
 *
 * @author home
 */

import PrivateZone.PrivateZone.domain.Cita;
import PrivateZone.PrivateZone.domain.Promocion;
import PrivateZone.PrivateZone.domain.ReservaMesa;
import PrivateZone.PrivateZone.dto.ResumenDia;
import PrivateZone.PrivateZone.repository.CitaRepository;
import PrivateZone.PrivateZone.repository.PromocionRepository;
import PrivateZone.PrivateZone.repository.ReservaMesaRepository;
import PrivateZone.PrivateZone.service.ResumenDiaService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumenDiaServiceImpl implements ResumenDiaService {

    // estados que reconoce el resumen; cualquier otro se muestra tal cual y cuenta como pendiente
    private static final String PENDIENTE = "PENDIENTE";
    private static final String CONFIRMADA = "CONFIRMADA";
    private static final String EN_CURSO = "EN_CURSO";
    private static final String COMPLETADA = "COMPLETADA";
    private static final String CANCELADA = "CANCELADA";

    private static final int DIAS_VENCE_PRONTO = 7;
    private static final Locale LOCALE = Locale.forLanguageTag("es-CR");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM, yyyy", LOCALE);

    private final CitaRepository citaRepository;
    private final ReservaMesaRepository reservaMesaRepository;
    private final PromocionRepository promocionRepository;

    public ResumenDiaServiceImpl(CitaRepository citaRepository, ReservaMesaRepository reservaMesaRepository,
            PromocionRepository promocionRepository) {
        this.citaRepository = citaRepository;
        this.reservaMesaRepository = reservaMesaRepository;
        this.promocionRepository = promocionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResumenDia getResumen(LocalDate fecha) {
        List<Cita> citas = citaRepository.findByFechaOrderByHoraAsc(fecha);
        List<ReservaMesa> mesas = reservaMesaRepository.findByFechaOrderByHoraAsc(fecha);

        ResumenDia resumen = new ResumenDia();
        String fechaTexto = fecha.format(FORMATO_FECHA);
        resumen.setFechaTexto(fechaTexto.substring(0, 1).toUpperCase(LOCALE) + fechaTexto.substring(1));
        resumen.setDiaSemana(fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, LOCALE));

        int completadas = 0;
        int canceladas = 0;
        long ventasSalon = 0;
        for (Cita c : citas) {
            String estado = normalizar(c.getEstado());
            if (COMPLETADA.equals(estado)) {
                completadas++;
                ventasSalon += c.getServicio().getPrecio();
            } else if (CANCELADA.equals(estado)) {
                canceladas++;
            }
            String especialista = c.getEspecialista() != null ? c.getEspecialista().getNombre() : "Sin preferencia";
            resumen.getCitas().add(fila(c.getHora(), c.getNombre(), c.getServicio().getNombre(), "Salón",
                    especialista, c.getEstado()));
        }
        for (ReservaMesa m : mesas) {
            String estado = normalizar(m.getEstado());
            if (COMPLETADA.equals(estado)) {
                completadas++;
            } else if (CANCELADA.equals(estado)) {
                canceladas++;
            }
            String detalle = "Reserva de mesa · " + m.getPersonas() + (m.getPersonas() == 1 ? " persona" : " personas");
            resumen.getCitas().add(fila(m.getHora(), m.getNombre(), detalle, "Café", "—", m.getEstado()));
        }
        resumen.getCitas().sort(Comparator.comparing(ResumenDia.FilaCita::getHora));

        int total = citas.size() + mesas.size();
        resumen.setTotalCitas(total);
        resumen.setCompletadas(completadas);
        resumen.setCanceladas(canceladas);
        resumen.setPendientes(total - completadas - canceladas);
        resumen.setVentasSalon(ventasSalon);

        // clientes atendidos = clientes distintos (por teléfono) con cita o reserva completada
        int atendidos = clientesAtendidos(citas, mesas);
        resumen.setClientesAtendidos(atendidos);
        LocalDate mismoDiaAnterior = fecha.minusWeeks(1);
        int anteriores = clientesAtendidos(citaRepository.findByFechaOrderByHoraAsc(mismoDiaAnterior),
                reservaMesaRepository.findByFechaOrderByHoraAsc(mismoDiaAnterior));
        if (anteriores > 0) {
            resumen.setVariacionClientes((int) Math.round((atendidos - anteriores) * 100.0 / anteriores));
        }

        // promociones: vigentes hoy, programadas (activas que aún no empiezan) y las que vencen en la semana
        int vigentes = 0;
        int programadas = 0;
        int vencenPronto = 0;
        for (Promocion p : promocionRepository.findByActivaTrueOrderByOrdenAsc()) {
            if (p.getFechaInicio() != null && p.getFechaInicio().isAfter(fecha)) {
                programadas++;
            } else if (p.getFechaFin() == null || !p.getFechaFin().isBefore(fecha)) {
                vigentes++;
                if (p.getFechaFin() != null && !p.getFechaFin().isAfter(fecha.plusDays(DIAS_VENCE_PRONTO))) {
                    vencenPronto++;
                }
            }
        }
        resumen.setPromocionesActivas(vigentes);
        resumen.setPromocionesProgramadas(programadas);
        resumen.setPromocionesVencenPronto(vencenPronto);
        return resumen;
    }

    private int clientesAtendidos(List<Cita> citas, List<ReservaMesa> mesas) {
        Set<String> telefonos = new HashSet<>();
        for (Cita c : citas) {
            if (COMPLETADA.equals(normalizar(c.getEstado()))) {
                telefonos.add(c.getTelefono().replaceAll("\\D", ""));
            }
        }
        for (ReservaMesa m : mesas) {
            if (COMPLETADA.equals(normalizar(m.getEstado()))) {
                telefonos.add(m.getTelefono().replaceAll("\\D", ""));
            }
        }
        return telefonos.size();
    }

    private ResumenDia.FilaCita fila(LocalTime hora, String cliente, String servicio, String area,
            String especialista, String estadoBd) {
        ResumenDia.FilaCita f = new ResumenDia.FilaCita();
        f.setHora(hora);
        f.setCliente(cliente);
        f.setServicio(servicio);
        f.setArea(area);
        f.setEspecialista(especialista);
        String estado = normalizar(estadoBd);
        f.setEstado(switch (estado) {
            case PENDIENTE -> "Pendiente";
            case CONFIRMADA -> "Confirmada";
            case EN_CURSO -> "En curso";
            case COMPLETADA -> "Completada";
            case CANCELADA -> "Cancelada";
            default -> estadoBd;
        });
        f.setEstadoClase(switch (estado) {
            case PENDIENTE -> "pz-res-pendiente";
            case CONFIRMADA -> "pz-res-confirmada";
            case EN_CURSO -> "pz-res-curso";
            case COMPLETADA -> "pz-res-completada";
            case CANCELADA -> "pz-res-cancelada";
            default -> "pz-res-otro";
        });
        return f;
    }

    private static String normalizar(String estado) {
        return estado == null ? "" : estado.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
    }
}
