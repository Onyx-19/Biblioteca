package com.bibliored.demo.component;

import com.bibliored.demo.enums.EstadoPrestamo;
import com.bibliored.demo.repository.PrestamoRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class ValidadorVencimiento {
    private final PrestamoRepository prestamoRepository;

    public ValidadorVencimiento(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "America/Bogota")
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void marcarVencidos(){
        prestamoRepository.marcarVencidos(
                EstadoPrestamo.ACTIVO, EstadoPrestamo.VENCIDO, LocalDate.now()
        );
    }
}
