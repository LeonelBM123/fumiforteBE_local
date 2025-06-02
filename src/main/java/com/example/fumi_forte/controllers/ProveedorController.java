/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.Proveedor;
import com.example.fumi_forte.repository.ProveedorRepository;
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

/**
 *
 * @author PC
 */
@RestController
public class ProveedorController {
    //CREAR
    @Autowired
    private ProveedorRepository proveedores;
    @BitacoraLog("Se creo un proveedor")
    @PostMapping("/nueva_proveedor")
    public ResponseEntity<?> registrarProveedor(@RequestBody Proveedor proveedor) {
        if (proveedores.existsByCorreo(proveedor.getCorreo())) {
            return ResponseEntity.badRequest().body("El proveedor ya está registrado con ese correo.");
        }
        Proveedor nuevoProveedor = proveedores.save(proveedor);
        return ResponseEntity.ok(nuevoProveedor);
    }
    
    //ELIMINAR
    @BitacoraLog("Se elimino un proveedor")
    @DeleteMapping("/proveedores/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        if (!proveedores.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proveedores.deleteById(id);
        return ResponseEntity.ok("Proveedor eliminada correctamente.");
    }
    
    //MODIFICAR
    @BitacoraLog("Se modifico un proveedor")
    @PutMapping("/proveedores/{id}")
    public ResponseEntity<?> actualizarProveedores(@PathVariable Long id, @RequestBody Proveedor proveedorActualizado) {
        Optional<Proveedor> proveedorOptional = proveedores.findById(id);
        if (!proveedorOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Proveedor proveedorExistente = proveedorOptional.get();
        // Actualizar los campos necesarios (excepto contraseña si no se quiere cambiar)
        proveedorExistente.setNombre(proveedorActualizado.getNombre());
        proveedorExistente.setTelefono(proveedorActualizado.getTelefono());
        proveedorExistente.setDireccion(proveedorActualizado.getDireccion());
        proveedorExistente.setCorreo(proveedorActualizado.getCorreo());
        proveedorExistente.setEstado(proveedorActualizado.getEstado());

        Proveedor proveedorModificado = proveedores.save(proveedorExistente);
        return ResponseEntity.ok(proveedorModificado);
    }
}
