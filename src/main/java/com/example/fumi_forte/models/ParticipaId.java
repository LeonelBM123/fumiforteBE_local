package com.example.fumi_forte.models;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipaId implements Serializable {
    private Long idTrabajador;
    private Long idSesion;
}
