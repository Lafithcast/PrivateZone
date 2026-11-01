package PrivateZone.PrivateZone.config;

import PrivateZone.PrivateZone.domain.Categoria;
import PrivateZone.PrivateZone.domain.Colaborador;
import PrivateZone.PrivateZone.domain.Promocion;
import PrivateZone.PrivateZone.repository.CategoriaRepository;
import PrivateZone.PrivateZone.repository.ColaboradorRepository;
import PrivateZone.PrivateZone.repository.PromocionRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Carga datos de ejemplo la primera vez que arranca la aplicación (si las tablas están vacías).
Carga automáticamente promociones, categorías y colaboradores de ejemplo al iniciar la aplicación, 
solo si las tablas están vacías, para que la página de inicio tenga contenido sin insertar datos manualmente. */
@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(PromocionRepository promociones,
                                  CategoriaRepository categorias,
                                  ColaboradorRepository colaboradores) {
        return args -> {
            if (promociones.count() == 0) {
                promociones.saveAll(List.of(
                    new Promocion("Café + Salón · Combo", "Desayuno + Manicura Express",
                        "Empezá el día con un desayuno de la casa y salí con las uñas listas. Todo en una sola visita.",
                        14500, 18000, "bi-stars", true, 1),
                    new Promocion("Café · Temporada", "Cold brew de la casa",
                        "Nuestro café frío de lote pequeño, infusionado por 18 horas. Llévalo o disfrútalo aquí.",
                        2900, null, "bi-cup-straw", true, 2),
                    new Promocion("Salón · Bienestar", "Limpieza facial + masaje relajante",
                        "Un ritual de una hora para renovar tu piel y soltar la tensión, con café de cortesía.",
                        32000, 40000, "bi-flower2", true, 3)
                ));
            }
            if (categorias.count() == 0) {
                categorias.saveAll(List.of(
                    new Categoria("CAFE", "Espresso y métodos", "Espresso, V60 y prensa francesa con café de altura.", 1800, "bi-cup-hot"),
                    new Categoria("CAFE", "Desayunos y brunch", "Gallo pinto de la casa, bowls y tostadas artesanales.", 4500, "bi-egg-fried"),
                    new Categoria("CAFE", "Repostería artesanal", "Queques, croissants y postres horneados cada mañana.", 2200, "bi-cake2"),
                    new Categoria("SALON", "Corte y Estilismo", "Cortes, peinados y asesoría según tu tipo de cabello.", 8000, "bi-scissors"),
                    new Categoria("SALON", "Barbería y Peluquería", "Corte clásico, fade, perfilado y arreglo de barba.", 6500, "bi-person-badge"),
                    new Categoria("SALON", "Uñas", "Manicura, pedicura, gel y diseños personalizados.", 7000, "bi-stars"),
                    new Categoria("SALON", "Tratamientos Faciales", "Limpieza profunda, hidratación y cuidado de la piel.", 18000, "bi-flower2")
                ));
            }
            if (colaboradores.count() == 0) {
                colaboradores.saveAll(List.of(
                    new Colaborador("CAFE", "Sendry Hernandez", "Barista principal", "SH"),
                    new Colaborador("CAFE", "Hellen Mora", "Barista", "HM"),
                    new Colaborador("SALON", "Daysi Araya", "Estilista", "DA"),
                    new Colaborador("SALON", "Beverly Chavarria", "Especialista en uñas", "BC"),
                    new Colaborador("SALON", "Roxana Alfaro", "Estilista", "RA")
                ));
            }
        };
    }
}