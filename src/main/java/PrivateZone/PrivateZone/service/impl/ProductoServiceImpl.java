/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package PrivateZone.PrivateZone.service.impl;

import PrivateZone.PrivateZone.domain.Producto;
import PrivateZone.PrivateZone.repository.ProductoRepository;
import PrivateZone.PrivateZone.service.ProductoService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mafer
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductosPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);
    }
}