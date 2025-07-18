package com.example.fumi_forte.repository;

import com.example.fumi_forte.models.Pago;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagoRepository extends JpaRepository<Pago, Long>{
    @Query("""
        SELECT COALESCE(SUM(p.monto), 0)
        FROM Pago p
        WHERE p.estado = 'PAGADO'
        AND EXTRACT(MONTH FROM p.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)
        AND EXTRACT(YEAR FROM p.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)
    """)
    BigDecimal sumMontosPagadosMesActual();
}
