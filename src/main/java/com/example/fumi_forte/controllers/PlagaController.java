package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.models.Plaga;
import com.example.fumi_forte.repository.PlagaRepository;
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
public class PlagaController {
    //CREAR
    @Autowired
    private PlagaRepository plagas;
    @BitacoraLog("Se registro una nueva plaga")
    @PostMapping("/nueva_plaga")
    public ResponseEntity<?> registrarPlaga(@RequestBody Plaga plaga) {
        if (plagas.existsByNombre(plaga.getNombre())) {
            return ResponseEntity.badRequest().body("La plaga ya está registrado.");
        }
        Plaga nuevoPlaga = plagas.save(plaga);
        return ResponseEntity.ok(nuevoPlaga);
    }
    
    //ELIMINAR
    @BitacoraLog("Se elimino una plaga")
    @DeleteMapping("/plagas/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<?> eliminarPlaga(@PathVariable Long id) {
        if (!plagas.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        plagas.deleteById(id);
        return ResponseEntity.ok("Plaga eliminada correctamente.");
    }
    
    //MODIFICAR
    @BitacoraLog("Se modifico una plaga")
    @PutMapping("/plagas/{id}")
    public ResponseEntity<?> actualizarPlaga(@PathVariable Long id, @RequestBody Plaga plagaActualizada) {
        Optional<Plaga> plagaOptional = plagas.findById(id);

        if (!plagaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Plaga plagaExistente = plagaOptional.get();

        // Actualizar los campos necesarios (excepto contraseña si no se quiere cambiar)
        plagaExistente.setNombre(plagaActualizada.getNombre());
        plagaExistente.setDescripcion(plagaActualizada.getDescripcion());
        plagaExistente.setRecomendaciones(plagaActualizada.getRecomendaciones());

        Plaga plagaModificado = plagas.save(plagaExistente);
        return ResponseEntity.ok(plagaModificado);
    }
}
