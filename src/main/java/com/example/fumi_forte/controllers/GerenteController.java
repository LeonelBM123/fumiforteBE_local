/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.fumi_forte.controllers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.UsuarioReporteDto;
import com.example.fumi_forte.models.Bitacora;
import com.example.fumi_forte.models.Plaga;
import com.example.fumi_forte.models.Producto;
import com.example.fumi_forte.models.Proveedor;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.BitacoraRepository;
import com.example.fumi_forte.repository.PlagaRepository;
import com.example.fumi_forte.repository.ProductoRepository;
import com.example.fumi_forte.repository.ProveedorRepository;
import com.example.fumi_forte.repository.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerente")
@RequiredArgsConstructor
public class GerenteController {
    @Autowired
    UsuarioRepository Usuarios;
    @Autowired
    PlagaRepository Plagas;
    @Autowired
    ProveedorRepository Proveedores;
    @Autowired
    BitacoraRepository Bitacoras;
    @Autowired
    ProductoRepository Productos;
  
    @BitacoraLog("Listar Usuarios")
    @GetMapping("/usuarios")
    @PreAuthorize("hasAuthority('Gerente')")
    public Page<UsuarioReporteDto> obtenerUsuarios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return Usuarios.findAllUsuariosPages(pageable);
    }
    
    
    
    @BitacoraLog("Listar Trabajadores")
    @GetMapping("/trabajadores")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<List<Usuario>> obtenerTrabajadores() {
        List<Usuario> trabajadores = Usuarios.findByRol("Trabajador");

        if (trabajadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(trabajadores);
    }

    
//    @PreAuthorize("hasAuthority('Gerente')")    
    @BitacoraLog("Listar Plagas")
    @GetMapping("/plagas")
    public List<Plaga> obtenerPlaga(){
        return Plagas.findAll();
    }
    
    @BitacoraLog("Listar Proveedores")
    @GetMapping("/proveedores")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<Proveedor> obtenerProveedores(){
        return Proveedores.findAll();
    }
    
    @BitacoraLog("Listar Productos")
    @GetMapping("/productos")
    @PreAuthorize("hasAuthority('Gerente')")
    public List<Producto> obtenerProductos(){
        return Productos.findAll();
    }
    

//    @BitacoraLog("Listar bitacora")

    //prueba de bitacoras con filtro asociacion

    
//    @PreAuthorize("hasAuthority('Gerente')")
    @GetMapping("/bitacoras")
    public List<Bitacora> obtenerBitacoras() {
        return Bitacoras.findAll();
    }
    
}

