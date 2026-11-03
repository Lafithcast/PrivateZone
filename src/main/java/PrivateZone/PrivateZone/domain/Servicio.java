package PrivateZone.PrivateZone.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "servicio")
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    private int duracionMinutos;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private boolean disponible = true;

    // categoría del Salón (area "SALON")
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // relación unidireccional: la tabla intermedia es servicio_especialista
    @ManyToMany
    @JoinTable(name = "servicio_especialista",
            joinColumns = @JoinColumn(name = "servicio_id"),
            inverseJoinColumns = @JoinColumn(name = "colaborador_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Colaborador> especialistas = new HashSet<>();

    public Servicio() {
    }

    public Servicio(String nombre, String descripcion, int duracionMinutos, Integer precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.categoria = categoria;
    }
}
