/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author mafer
 */
@Data
@Entity
@Table(name = "alergeno")
public class Alergeno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String nombre; 

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Alergeno() {
    }

    public Alergeno(String nombre) {
        this.nombre = nombre;
    }
}