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

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;                  // rol o especialidad

    @Column(nullable = false, length = 10)
    private String estado = "ACTIVO"; // "ACTIVO" o "INACTIVO"

    @Column(nullable = false, length = 4)
    private String iniciales;

    // credenciales del Portal de colaboradores; null = el colaborador no tiene acceso
    @Column(unique = true, length = 100)
    private String correo;            // correo corporativo (es el usuario)

    @Column(length = 100)
    private String password;          // hash BCrypt, nunca texto plano

    public Colaborador() { }

    public Colaborador(String area, String nombre, Rol rol, String iniciales) {
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
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getIniciales() { return iniciales; }
    public void setIniciales(String iniciales) { this.iniciales = iniciales; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}