package com.example.fumi_forte.repository;

import com.example.fumi_forte.dto.PagoDto;
import com.example.fumi_forte.models.Pago;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagoRepository extends JpaRepository<Pago, Long>{
    @Query("""
        SELECT COALESCE(SUM(p.monto), 0)
        FROM Pago p
        WHERE p.estado = 'Pagado'
        AND EXTRACT(MONTH FROM p.fecha) = EXTRACT(MONTH FROM CURRENT_DATE)
        AND EXTRACT(YEAR FROM p.fecha) = EXTRACT(YEAR FROM CURRENT_DATE)
    """)
    BigDecimal sumMontosPagadosMesActual();
    
    @Query("SELECT new com.example.fumi_forte.dto.PagoDto(" +
            "p.idPago, p.fecha, p.monto, p.tipoPago, p.nroVoucher, p.estado, p.cliente.idCliente) " +
            "FROM Pago p ORDER BY p.fecha DESC")
     List<PagoDto> findUltimosPagos(Pageable pageable);


}
