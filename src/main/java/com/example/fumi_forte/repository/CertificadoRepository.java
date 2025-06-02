
package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.CertificadoFumigacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CertificadoRepository extends JpaRepository<CertificadoFumigacion, Long>  {
    
}
