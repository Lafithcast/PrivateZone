package PrivateZone.PrivateZone.controllers;

import PrivateZone.PrivateZone.domain.Cita;
import PrivateZone.PrivateZone.domain.Colaborador;
import PrivateZone.PrivateZone.domain.Servicio;
import PrivateZone.PrivateZone.dto.CitaForm;
import PrivateZone.PrivateZone.service.CitaService;
import PrivateZone.PrivateZone.service.ServicioService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/salon/agendar")
@SessionAttributes("citaForm")
public class AgendarCitaController {

    private static final Pattern TELEFONO = Pattern.compile("[0-9 +\\-]+");

    private final ServicioService servicioService;
    private final CitaService citaService;

    public AgendarCitaController(ServicioService servicioService, CitaService citaService) {
        this.servicioService = servicioService;
        this.citaService = citaService;
    }

    @ModelAttribute("citaForm")
    public CitaForm crearForm() {
        return new CitaForm();
    }

    // ---------- Paso 1: servicio y especialista ----------

    @GetMapping
    public String paso1(Model model) {
        prepararPaso1(model);
        return "salon/agendar-paso1";
    }

    @PostMapping
    public String guardarPaso1(@ModelAttribute("citaForm") CitaForm form, BindingResult binding, Model model) {
        List<String> errores = validarPaso1(form, cargarServicio(form));
        if (binding.hasErrors() && errores.isEmpty()) {
            errores.add("agenda.error.datos");
        }
        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            prepararPaso1(model);
            return "salon/agendar-paso1";
        }
        return "redirect:/salon/agendar/fecha";
    }

    // ---------- Paso 2: fecha y hora ----------

    @GetMapping("/fecha")
    public String paso2(@ModelAttribute("citaForm") CitaForm form, Model model) {
        Servicio servicio = cargarServicio(form);
        if (!validarPaso1(form, servicio).isEmpty()) {
            return "redirect:/salon/agendar";
        }
        prepararPaso2(model, form, servicio);
        return "salon/agendar-paso2";
    }

    @PostMapping("/fecha")
    public String guardarPaso2(@ModelAttribute("citaForm") CitaForm form, BindingResult binding, Model model) {
        Servicio servicio = cargarServicio(form);
        if (!validarPaso1(form, servicio).isEmpty()) {
            return "redirect:/salon/agendar";
        }
        List<String> errores = validarPaso2(form, servicio);
        if (binding.hasErrors() && errores.isEmpty()) {
            errores.add("agenda.error.datos");
        }
        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            prepararPaso2(model, form, servicio);
            return "salon/agendar-paso2";
        }
        return "redirect:/salon/agendar/resumen";
    }

    // ---------- Paso 3: resumen y datos del cliente ----------

    @GetMapping("/resumen")
    public String paso3(@ModelAttribute("citaForm") CitaForm form, Model model) {
        Servicio servicio = cargarServicio(form);
        if (!validarPaso1(form, servicio).isEmpty()) {
            return "redirect:/salon/agendar";
        }
        if (!validarPaso2(form, servicio).isEmpty()) {
            return "redirect:/salon/agendar/fecha";
        }
        prepararPaso3(model, form, servicio);
        return "salon/agendar-paso3";
    }

    @PostMapping("/confirmar")
    public String confirmar(@ModelAttribute("citaForm") CitaForm form, BindingResult binding, Model model,
            RedirectAttributes redirect, SessionStatus status) {
        // se valida todo de nuevo, no solo el último paso
        Servicio servicio = cargarServicio(form);
        List<String> errores = validarPaso1(form, servicio);
        if (!errores.isEmpty()) {
            redirect.addFlashAttribute("errores", errores);
            return "redirect:/salon/agendar";
        }
        errores = validarPaso2(form, servicio);
        if (!errores.isEmpty()) {
            redirect.addFlashAttribute("errores", errores);
            return "redirect:/salon/agendar/fecha";
        }
        errores = validarPaso3(form);
        if (!errores.isEmpty()) {
            model.addAttribute("errores", errores);
            prepararPaso3(model, form, servicio);
            return "salon/agendar-paso3";
        }

        Cita cita = new Cita();
        cita.setServicio(servicio);
        // el especialista sale de los especialistas recién cargados del servicio
        cita.setEspecialista(buscarEspecialista(servicio, form.getEspecialistaId()));
        cita.setNombre(form.getNombre());
        cita.setTelefono(form.getTelefono());
        cita.setFecha(form.getFecha());
        cita.setHora(form.getHora());
        citaService.guardar(cita);

        status.setComplete();
        redirect.addFlashAttribute("citaOk", true);
        return "redirect:/salon";
    }

    // ---------- Validaciones ----------

    private List<String> validarPaso1(CitaForm form, Servicio servicio) {
        List<String> errores = new ArrayList<>();
        if (servicio == null || !servicio.isDisponible()) {
            errores.add("agenda.error.servicio");
        } else if (form.getEspecialistaId() != null && buscarEspecialista(servicio, form.getEspecialistaId()) == null) {
            errores.add("agenda.error.especialista");
        }
        return errores;
    }

    private List<String> validarPaso2(CitaForm form, Servicio servicio) {
        List<String> errores = new ArrayList<>();
        if (form.getFecha() == null) {
            errores.add("agenda.error.fecha");
        } else if (form.getFecha().isBefore(LocalDate.now())) {
            errores.add("agenda.error.fechaPasada");
        } else if (!citaService.esDiaLaboral(form.getFecha())) {
            errores.add("agenda.error.diaNoLaboral");
        }
        if (form.getHora() == null || !citaService.getHorasInicio(servicio).contains(form.getHora())) {
            errores.add("agenda.error.hora");
        }
        // con "sin preferencia" (especialistaId nulo) no se revisa la disponibilidad
        if (errores.isEmpty() && form.getEspecialistaId() != null
                && citaService.especialistaOcupado(form.getEspecialistaId(), form.getFecha(),
                        form.getHora(), servicio.getDuracionMinutos())) {
            errores.add("agenda.error.ocupado");
        }
        return errores;
    }

    private List<String> validarPaso3(CitaForm form) {
        List<String> errores = new ArrayList<>();
        String nombre = form.getNombre() == null ? "" : form.getNombre().trim();
        String telefono = form.getTelefono() == null ? "" : form.getTelefono().trim();
        form.setNombre(nombre);
        form.setTelefono(telefono);
        if (nombre.isEmpty() || nombre.length() > 80) {
            errores.add("agenda.error.nombre");
        }
        if (telefono.isEmpty() || telefono.length() > 20 || !TELEFONO.matcher(telefono).matches()) {
            errores.add("agenda.error.telefono");
        }
        return errores;
    }

    // ---------- Apoyo ----------

    private Servicio cargarServicio(CitaForm form) {
        if (form.getServicioId() == null) {
            return null;
        }
        return servicioService.buscarPorId(form.getServicioId()).orElse(null);
    }

    // devuelve el especialista activo del servicio con ese id, o null
    private Colaborador buscarEspecialista(Servicio servicio, Long especialistaId) {
        if (especialistaId == null) {
            return null;
        }
        for (Colaborador c : servicio.getEspecialistas()) {
            if (c.getId().equals(especialistaId) && "ACTIVO".equals(c.getEstado())) {
                return c;
            }
        }
        return null;
    }

    private void prepararPaso1(Model model) {
        // servicios agrupados por nombre de categoría, en el orden de la consulta
        Map<String, List<Servicio>> porCategoria = new LinkedHashMap<>();
        Map<Long, Colaborador> especialistas = new LinkedHashMap<>();
        for (Servicio s : servicioService.getServiciosDisponiblesConEspecialistas()) {
            porCategoria.computeIfAbsent(s.getCategoria().getNombre(), k -> new ArrayList<>()).add(s);
            for (Colaborador c : s.getEspecialistas()) {
                if ("ACTIVO".equals(c.getEstado())) {
                    especialistas.put(c.getId(), c);
                }
            }
        }
        List<Colaborador> lista = new ArrayList<>(especialistas.values());
        lista.sort(Comparator.comparing(Colaborador::getNombre));
        model.addAttribute("serviciosPorCategoria", porCategoria);
        model.addAttribute("especialistas", lista);
    }

    private void prepararPaso2(Model model, CitaForm form, Servicio servicio) {
        model.addAttribute("servicio", servicio);
        model.addAttribute("especialista", buscarEspecialista(servicio, form.getEspecialistaId()));
        model.addAttribute("horas", citaService.getHorasInicio(servicio));
        model.addAttribute("hoy", LocalDate.now());
    }

    private void prepararPaso3(Model model, CitaForm form, Servicio servicio) {
        model.addAttribute("servicio", servicio);
        model.addAttribute("especialista", buscarEspecialista(servicio, form.getEspecialistaId()));
    }
}
