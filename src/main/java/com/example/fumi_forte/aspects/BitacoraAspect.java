
package com.example.fumi_forte.aspects;

import com.example.fumi_forte.services.BitacoraService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class BitacoraAspect {

    private final BitacoraService bitacoraService;

    @Around("@annotation(bitacoraLog) && within(@org.springframework.web.bind.annotation.RestController *)")
    public Object registrarBitacora(ProceedingJoinPoint joinPoint, BitacoraLog bitacoraLog) throws Throwable {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (attrs != null) ? attrs.getRequest() : null;

        String ip = (request != null) ? obtenerIpReal(request) : "0.0.0.0";
        String accion = bitacoraLog.value();

        Object result = null;
        try {
            result = joinPoint.proceed(); // Ejecuta el método del endpoint
            bitacoraService.registrar(ip, accion); // Registra solo si no hay excepción
        } catch (Throwable ex) {
            bitacoraService.registrar(ip, accion + " (FALLÓ)");
            throw ex;
        }

        return result;
    }

    private String obtenerIpReal(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        return (xfHeader != null) ? xfHeader.split(",")[0] : request.getRemoteAddr();
    }
}