package PrivateZone.PrivateZone.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "promocion")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String etiqueta;          // Ej.: "Café + Salón · Combo"

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;           // en colones

    private Integer precioAnterior;   // opcional, para mostrar "antes"

    @Column(length = 40)
    private String icono = "bi-stars"; // clase de Bootstrap Icons

    private boolean activa = true;

    private Integer orden = 0;

    private LocalDate fechaInicio;    // opcional: null = sin límite

    private LocalDate fechaFin;       // opcional: null = sin límite

    public Promocion() { }

    public Promocion(String etiqueta, String titulo, String descripcion, Integer precio,
                     Integer precioAnterior, String icono, boolean activa, Integer orden) {
        this.etiqueta = etiqueta;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioAnterior = precioAnterior;
        this.icono = icono;
        this.activa = activa;
        this.orden = orden;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEtiqueta() { return etiqueta; }
    public void setEtiqueta(String etiqueta) { this.etiqueta = etiqueta; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getPrecio() { return precio; }
    public void setPrecio(Integer precio) { this.precio = precio; }
    public Integer getPrecioAnterior() { return precioAnterior; }
    public void setPrecioAnterior(Integer precioAnterior) { this.precioAnterior = precioAnterior; }
    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}