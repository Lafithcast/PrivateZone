/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.config;

import PrivateZone.PrivateZone.domain.Categoria;
import PrivateZone.PrivateZone.domain.Producto;
import PrivateZone.PrivateZone.repository.CategoriaRepository;
import PrivateZone.PrivateZone.repository.ProductoRepository;
import java.util.List;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author mafer
 */
@Component
public class DatosCafe {

    private final ProductoRepository productos;
    private final CategoriaRepository categorias;

    public DatosCafe(ProductoRepository productos, CategoriaRepository categorias) {
        this.productos = productos;
        this.categorias = categorias;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void cargarCafe() {
        if (productos.count() > 0) {
            return;
        }

        List<Categoria> cafe = categorias.findByAreaOrderByIdAsc("CAFE");
        if (cafe.size() < 3) {
            return;
        }

        Categoria espresso = cafe.get(0); // Espresso y métodos
        Categoria desayunos = cafe.get(1); // Desayunos y brunch
        Categoria reposteria = cafe.get(2); // Repostería artesanal

        Producto p1 = new Producto("Espresso doble",
                "Shot doble de Tarrazú, notas de chocolate oscuro y caramelo.",
                1800, espresso);

        Producto p2 = new Producto("Capuchino de la casa",
                "Espresso doble con leche texturizada y un toque de canela.",
                2600, espresso);
        p2.agregarAlergeno("lácteos");

        Producto p3 = new Producto("Latte de vainilla",
                "Espresso suave, leche cremosa y vainilla natural.",
                2900, espresso);
        p3.agregarAlergeno("lácteos");

        Producto p4 = new Producto("Gallo pinto de la casa",
                "Con huevo, natilla y plátano.", 4500, desayunos);

        Producto p5 = new Producto("Croissant de almendra",
                "Horneado cada mañana.", 2200, reposteria);

        productos.saveAll(List.of(p1, p2, p3, p4, p5));
    }
}