/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package PrivateZone.PrivateZone.service;

import PrivateZone.PrivateZone.domain.Producto;
import java.util.List;

/**
 *
 * @author mafer
 */
public interface ProductoService {

    public List<Producto> getProductos();

    public List<Producto> getProductosPorCategoria(Long categoriaId);
}
