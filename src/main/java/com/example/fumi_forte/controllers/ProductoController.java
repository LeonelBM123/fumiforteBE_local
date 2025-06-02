/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.Producto;
import com.example.fumi_forte.repository.ProductoRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductoController {
    //CREAR
    @Autowired
    private ProductoRepository productos;
    @BitacoraLog("Se creo un producto")
    @PostMapping("/nuevo_producto")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto) {
        if (productos.existsByNombre(producto.getNombre())) {
            return ResponseEntity.badRequest().body("El producto ya está registrado.");
        }
        Producto nuevoProducto = productos.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }
    
    //ELIMINAR
    @BitacoraLog("Se elimino un producto")
    @DeleteMapping("/productos/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        if (!productos.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productos.deleteById(id);
        return ResponseEntity.ok("Producto eliminada correctamente.");
    }
    
    //MODIFICAR
    @BitacoraLog("Se modifico un producto")
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Optional<Producto> productoOptional = productos.findById(id);

        if (!productoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Producto productoExistente = productoOptional.get();

        // Actualizar los campos necesarios (excepto contraseña si no se quiere cambiar)
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setUnidadMedida(productoActualizado.getUnidadMedida());
        productoExistente.setStock(productoActualizado.getStock());
        productoExistente.setFechaVencimiento(productoActualizado.getFechaVencimiento());
        Producto productoModificado = productos.save(productoExistente);
        return ResponseEntity.ok(productoModificado);
    }
}
