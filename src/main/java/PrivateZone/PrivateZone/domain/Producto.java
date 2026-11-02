/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author mafer
 */
@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio; 

    @Column(nullable = false)
    private boolean disponible = true;

    @Column(nullable = false)
    private boolean paraLlevar = true;

    @Column(length = 300)
    private String imagenUrl; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alergeno> alergenos = new ArrayList<>();

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Integer precio, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
    }

    public void agregarAlergeno(String nombre) {
        Alergeno a = new Alergeno(nombre);
        a.setProducto(this);
        alergenos.add(a);
    }
}