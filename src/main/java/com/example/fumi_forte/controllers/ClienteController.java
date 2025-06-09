package com.example.fumi_forte.controllers;

import com.example.fumi_forte.aspects.BitacoraLog;
import com.example.fumi_forte.dto.UsuarioClienteDto;
import com.example.fumi_forte.dto.UsuarioTrabajadorDto;
import com.example.fumi_forte.dto.UsuarioUpdateDto;
import com.example.fumi_forte.models.Usuario;
import com.example.fumi_forte.repository.UsuarioRepository;
import com.example.fumi_forte.models.Cliente;
import com.example.fumi_forte.repository.ClienteRepository;
import com.example.fumi_forte.models.Trabajador;
import com.example.fumi_forte.repository.TrabajadorRepository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
public class ClienteController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TrabajadorRepository trabajadorRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    
    // GET: Listar todos los Clientes
    @BitacoraLog("Se ha listado todos los clientes")
    @GetMapping("/listar_clientes")
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteRepository.findAll());
        }
    
    // GET: Listar todos los Trabajadores
    @BitacoraLog("Se ha listado todos los trabajadores")
    @GetMapping("/listar_trabajadores")
    public ResponseEntity<?> listarTrabajadores() {
        return ResponseEntity.ok(trabajadorRepository.findAll());
        }
    
    // GET: Listar todos los Usuarios
    @BitacoraLog("Se ha listado todos los usuarios")
    @GetMapping("/listar_usuarios")
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
        }
    
    // POST: Crear Usuario Cliente
    //@BitacoraLog("Se ha registrado un nuevo Cliente")
    @PostMapping("/registrar_cliente")
    public ResponseEntity<?> registrarCliente(@RequestBody UsuarioClienteDto dto) {
        try {
            // Verificar si el correo ya está registrado
            if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
                return ResponseEntity.badRequest().body("El correo ya está registrado.");
            }

            // Crear y guardar usuario
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setNombreCompleto(dto.getNombreCompleto());
            usuarioNuevo.setTelefono(dto.getTelefono());
            usuarioNuevo.setDireccion(dto.getDireccion());
            usuarioNuevo.setCorreo(dto.getCorreo());
            usuarioNuevo.setRol(dto.getRol());
            usuarioNuevo.setEstado(dto.getEstado());
            usuarioNuevo.setContraseña(passwordEncoder.encode(dto.getContraseña()));

            Usuario usuarioGuardado = usuarioRepository.save(usuarioNuevo);

            // Crear y guardar cliente
            Cliente cliente = new Cliente();
            cliente.setTipoCliente(dto.getTipoCliente());
            cliente.setRazonSocial(dto.getRazonSocial());
            cliente.setNit(dto.getNit());
            cliente.setUsuario(usuarioGuardado); 

            clienteRepository.save(cliente);

            return ResponseEntity.ok("Cliente registrado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al registrar cliente: " + e.getMessage());
        }
    }

    // POST: Crear Usuario Trabajador
    @BitacoraLog("Se ha registrado un nuevo Trabajador")
    @PostMapping("/registrar_trabajador")
    public ResponseEntity<?> registrarTrabajador(@RequestBody UsuarioTrabajadorDto dto) {
        try {
            // Verificar si el correo ya está registrado
            if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
                return ResponseEntity.badRequest().body("El correo ya está registrado.");
            }

            // Crear y guardar usuario
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setNombreCompleto(dto.getNombreCompleto());
            usuarioNuevo.setTelefono(dto.getTelefono());
            usuarioNuevo.setDireccion(dto.getDireccion());
            usuarioNuevo.setCorreo(dto.getCorreo());
            usuarioNuevo.setRol(dto.getRol());
            usuarioNuevo.setEstado(dto.getEstado());
            usuarioNuevo.setContraseña(passwordEncoder.encode(dto.getContraseña()));

            Usuario usuarioGuardado = usuarioRepository.save(usuarioNuevo);

            // Crear y guardar cliente
            Trabajador trabajadorNuevo = new Trabajador();
            trabajadorNuevo.setEspecialidad(dto.getEspecialidad());
            trabajadorNuevo.setFechaInicio(dto.getFechaInicio());
            trabajadorNuevo.setUsuario(usuarioGuardado); 

            trabajadorRepository.save(trabajadorNuevo);

            return ResponseEntity.ok("Trabajador registrado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al registrar Trabajador: " + e.getMessage());
        }
    }
    
    // GET: Buscar Cliente Especifico por ID
    @BitacoraLog("Se ha buscado un Cliente")
    @GetMapping("/usuario/cliente/{idUsuario}")
    public ResponseEntity<?> buscarClientePorIdUsuario(@PathVariable Long idUsuario) {
        Optional<Cliente> clienteOpt = clienteRepository.findByUsuario_IdUsuario(idUsuario);

        if (clienteOpt.isPresent()) {
            return ResponseEntity.ok(clienteOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No existe cliente para el usuario con id: " + idUsuario);
        }
    }

    // GET: Buscar Trabajador Especifico por ID
    @BitacoraLog("Se ha buscado un Trabajador")
    @GetMapping("/usuario/trabajador/{idUsuario}")
    public ResponseEntity<?> buscarTrabajadorPorIdUsuario(@PathVariable Long idUsuario) {
        Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByUsuario_IdUsuario(idUsuario);

        if (trabajadorOpt.isPresent()) {
            return ResponseEntity.ok(trabajadorOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No existe cliente para el usuario con id: " + idUsuario);
        }
    }
    
    // DELETE: Eliminar usuario por ID
    @BitacoraLog("Se ha eliminado un Usuario")
    @DeleteMapping("/eliminar_usuario/{id}")
    @PreAuthorize("hasAuthority('Gerente')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }
    
    // PUT: Modificar Usuario por ID
    @BitacoraLog("Se ha modificado un Usuario")
    @PutMapping("/modificar_usuario/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDto userUpdateDto) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioExistente = usuarioOptional.get();

        if (userUpdateDto.getClienteDto() != null) {
            UsuarioClienteDto dto = userUpdateDto.getClienteDto();

            usuarioExistente.setNombreCompleto(dto.getNombreCompleto());
            usuarioExistente.setCorreo(dto.getCorreo());
            usuarioExistente.setTelefono(dto.getTelefono());
            usuarioExistente.setDireccion(dto.getDireccion());
            usuarioExistente.setEstado(dto.getEstado());
            usuarioExistente.setRol("Cliente");

            if (dto.getContraseña() != null && !dto.getContraseña().isEmpty()) {
                usuarioExistente.setContraseña(passwordEncoder.encode(dto.getContraseña()));
            }

            Usuario usuarioModificado = usuarioRepository.save(usuarioExistente);

            // Actualizar Cliente
            Optional<Cliente> clienteOptional = clienteRepository.findById(id);
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                cliente.setTipoCliente(dto.getTipoCliente());
                cliente.setRazonSocial(dto.getRazonSocial());
                cliente.setNit(dto.getNit());
                clienteRepository.save(cliente);
            }

            return ResponseEntity.ok(usuarioModificado);
        }

        if (userUpdateDto.getTrabajadorDto() != null) {
            UsuarioTrabajadorDto dto = userUpdateDto.getTrabajadorDto();

            usuarioExistente.setNombreCompleto(dto.getNombreCompleto());
            usuarioExistente.setCorreo(dto.getCorreo());
            usuarioExistente.setTelefono(dto.getTelefono());
            usuarioExistente.setDireccion(dto.getDireccion());
            usuarioExistente.setEstado(dto.getEstado());
            usuarioExistente.setRol("Trabajador");

            if (dto.getContraseña() != null && !dto.getContraseña().isEmpty()) {
                usuarioExistente.setContraseña(passwordEncoder.encode(dto.getContraseña()));
            }

            Usuario usuarioModificado = usuarioRepository.save(usuarioExistente);

            // Actualizar Trabajador
            Optional<Trabajador> trabajadorOptional = trabajadorRepository.findById(id);
            if (trabajadorOptional.isPresent()) {
                Trabajador trabajador = trabajadorOptional.get();
                trabajador.setEspecialidad(dto.getEspecialidad());
                trabajador.setFechaInicio(dto.getFechaInicio());
                trabajadorRepository.save(trabajador);
            }

            return ResponseEntity.ok(usuarioModificado);
        }

        return ResponseEntity.badRequest().body("No se proporcionó información válida para Cliente ni Trabajador.");
    }
    
    // PUT: Modificar solo datos de un usuario por ID
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuarioExistente = usuarioOptional.get();

        // Actualizar los campos necesarios (excepto contraseña si no se quiere cambiar)
        usuarioExistente.setNombreCompleto(usuarioActualizado.getNombreCompleto());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
        usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
        usuarioExistente.setContraseña(usuarioActualizado.getContraseña());
        usuarioExistente.setRol(usuarioActualizado.getRol());
        usuarioExistente.setEstado(usuarioActualizado.getEstado());

        // Si se quiere actualizar la contraseña, se debe volver a codificar
        if (usuarioActualizado.getContraseña() != null && !usuarioActualizado.getContraseña().isEmpty()) {
            usuarioExistente.setContraseña(passwordEncoder.encode(usuarioActualizado.getContraseña()));
        }
        Usuario usuarioModificado = usuarioRepository.save(usuarioExistente);
        return ResponseEntity.ok(usuarioModificado);
    }
}
