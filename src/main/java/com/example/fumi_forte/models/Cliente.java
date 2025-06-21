package com.example.fumi_forte.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(name = "id_cliente")
    private Long idCliente; 

    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private String tipoCliente;

    @Column(name = "razon_social", length = 50)
    private String razonSocial;

    @Column(name = "nit", length = 50)
    private String nit;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "id_cliente") 
    private Usuario usuario;
    
    public Cliente(Long idCliente) {
        this.idCliente = idCliente;
    }
}


