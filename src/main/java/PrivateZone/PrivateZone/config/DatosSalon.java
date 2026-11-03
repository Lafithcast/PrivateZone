package PrivateZone.PrivateZone.config;

import PrivateZone.PrivateZone.domain.Categoria;
import PrivateZone.PrivateZone.domain.Colaborador;
import PrivateZone.PrivateZone.domain.Servicio;
import PrivateZone.PrivateZone.repository.CategoriaRepository;
import PrivateZone.PrivateZone.repository.ColaboradorRepository;
import PrivateZone.PrivateZone.repository.ServicioRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatosSalon {

    private final ServicioRepository servicios;
    private final CategoriaRepository categorias;
    private final ColaboradorRepository colaboradores;

    public DatosSalon(ServicioRepository servicios, CategoriaRepository categorias,
                      ColaboradorRepository colaboradores) {
        this.servicios = servicios;
        this.categorias = categorias;
        this.colaboradores = colaboradores;
    }

    // se ejecuta al estar lista la aplicación, después de DatosIniciales (categorías y colaboradores)
    @EventListener(ApplicationReadyEvent.class)
    public void cargarSalon() {
        if (servicios.count() > 0) {
            return;
        }

        // las categorías se buscan por nombre, no por posición
        Map<String, Categoria> porNombre = new HashMap<>();
        for (Categoria c : categorias.findByAreaOrderByIdAsc("SALON")) {
            porNombre.put(c.getNombre(), c);
        }

        List<Colaborador> salon = colaboradores.findByAreaOrderByIdAsc("SALON");
        List<Colaborador> estilistas = conRol(salon, "Estilista");
        List<Colaborador> unias = conRol(salon, "Especialista en uñas");

        // datos de ejemplo: servicios de demostración; el más barato de cada categoría coincide con su precioDesde
        List<Servicio> lista = new ArrayList<>();

        Categoria corte = porNombre.get("Corte y Estilismo");
        if (corte != null) {
            lista.add(crear("Corte de cabello", "Corte según tu tipo de cabello.", 45, 8000, corte, estilistas));
            lista.add(crear("Peinado", "Peinado para el día o para una ocasión especial.", 45, 10000, corte, estilistas));
            lista.add(crear("Corte y peinado", "Corte con peinado incluido.", 75, 15000, corte, estilistas));
        }

        Categoria barberia = porNombre.get("Barbería y Peluquería");
        if (barberia != null) {
            lista.add(crear("Corte clásico", "Corte clásico con perfilado.", 30, 6500, barberia, estilistas));
            lista.add(crear("Arreglo de barba", "Perfilado y arreglo de barba.", 30, 7000, barberia, estilistas));
            lista.add(crear("Corte y barba", "Corte clásico más arreglo de barba.", 60, 11000, barberia, estilistas));
        }

        Categoria unas = porNombre.get("Uñas");
        if (unas != null) {
            lista.add(crear("Manicura", "Manicura con esmaltado.", 45, 7000, unas, unias));
            lista.add(crear("Pedicura", "Pedicura con esmaltado.", 60, 9000, unas, unias));
            lista.add(crear("Uñas en gel", "Aplicación de gel con diseño sencillo.", 90, 15000, unas, unias));
        }

        // sin especialistas: ningún colaborador existente tiene un rol facial
        Categoria facial = porNombre.get("Tratamientos Faciales");
        if (facial != null) {
            lista.add(crear("Limpieza facial profunda", "Limpieza profunda de la piel.", 60, 18000, facial, List.of()));
            lista.add(crear("Tratamiento hidratante", "Hidratación y cuidado de la piel.", 60, 22000, facial, List.of()));
        }

        servicios.saveAll(lista);
    }

    private List<Colaborador> conRol(List<Colaborador> lista, String nombreRol) {
        List<Colaborador> resultado = new ArrayList<>();
        for (Colaborador c : lista) {
            if (c.getRol() != null && nombreRol.equals(c.getRol().getNombre())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    private Servicio crear(String nombre, String descripcion, int duracion, Integer precio,
                           Categoria categoria, List<Colaborador> especialistas) {
        Servicio s = new Servicio(nombre, descripcion, duracion, precio, categoria);
        s.getEspecialistas().addAll(especialistas);
        return s;
    }
}
