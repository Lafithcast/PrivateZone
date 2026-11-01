package PrivateZone.PrivateZone.domain;

import jakarta.persistence.*;

/** Persona destacada del equipo (baristas del Café o especialistas del Salón). */
@Entity
@Table(name = "colaborador")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String area;              // "CAFE" o "SALON"

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String rol;               // rol o especialidad

    @Column(nullable = false, length = 4)
    private String iniciales;

    public Colaborador() { }

    public Colaborador(String area, String nombre, String rol, String iniciales) {
        this.area = area;
        this.nombre = nombre;
        this.rol = rol;
        this.iniciales = iniciales;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getIniciales() { return iniciales; }
    public void setIniciales(String iniciales) { this.iniciales = iniciales; }
}