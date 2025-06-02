package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Participa;
import com.example.fumi_forte.models.ParticipaId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipaRepository extends JpaRepository<Participa, ParticipaId> {
    List<Participa> findBySesion_IdSesion(Long idSesion);

    List<Participa> findByTrabajador_IdTrabajador(Long idTrabajador);

    List<Participa> findByTrabajador_IdTrabajadorAndSesion_IdSesion(Long idTrabajador, Long idSesion);

}
