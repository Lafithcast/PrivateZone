package PrivateZone.PrivateZone.domain;

import jakarta.persistence.*;

/** Categoría de productos del Café o de servicios del Salón (con precio de referencia). */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String area;              // "CAFE" o "SALON"

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Integer precioDesde;      // en colones

    @Column(length = 40)
    private String icono;             // clase de Bootstrap Icons

    public Categoria() { }

    public Categoria(String area, String nombre, String descripcion, Integer precioDesde, String icono) {
        this.area = area;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioDesde = precioDesde;
        this.icono = icono;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getPrecioDesde() { return precioDesde; }
    public void setPrecioDesde(Integer precioDesde) { this.precioDesde = precioDesde; }
    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
}