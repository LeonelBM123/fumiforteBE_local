package com.example.fumi_forte.specifications;

import com.example.fumi_forte.dto.UsuarioFiltroDto;
import com.example.fumi_forte.models.Usuario;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecifications {

    public static Specification<Usuario> filtrar(UsuarioFiltroDto filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.getIdUsuario() != null) {
                predicates.add(cb.equal(root.get("idUsuario"), filtro.getIdUsuario()));
            }
            if (filtro.getNombre() != null && !filtro.getNombre().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nombreCompleto")), "%" + filtro.getNombre().toLowerCase() + "%"));
            }
            if (filtro.getTelefono() != null && !filtro.getTelefono().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("telefono")), "%" + filtro.getTelefono().toLowerCase() + "%"));
            }
            if (filtro.getDireccion() != null && !filtro.getDireccion().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("direccion")), "%" + filtro.getDireccion().toLowerCase() + "%"));
            }
            if (filtro.getCorreo() != null && !filtro.getCorreo().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("correo")), "%" + filtro.getCorreo().toLowerCase() + "%"));
            }
            if (filtro.getRol() != null && !filtro.getRol().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("rol")), filtro.getRol().toLowerCase()));
            }
            if (filtro.getEstado() != null && !filtro.getEstado().isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("estado")), filtro.getEstado().toLowerCase()));
            }

            if (filtro.getTipoCliente() != null && !filtro.getTipoCliente().equals("-")) {
                Join<Object, Object> clienteJoin = root.join("cliente", JoinType.LEFT);
                predicates.add(cb.equal(cb.lower(clienteJoin.get("tipoCliente")), filtro.getTipoCliente().toLowerCase()));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
