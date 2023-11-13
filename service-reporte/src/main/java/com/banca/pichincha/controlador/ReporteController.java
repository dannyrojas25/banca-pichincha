package com.banca.pichincha.controlador;

import com.banca.pichincha.dto.EstadoCuentaDTO;
import com.banca.pichincha.servicio.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping()
    public ResponseEntity<EstadoCuentaDTO> generarReporte(
            @RequestParam("id") Long id,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio) {
        EstadoCuentaDTO estadoCuentaDTO = reporteService.generarReporte(id, fechaInicio, fechaFin);

        if (estadoCuentaDTO != null) {
            return ResponseEntity.ok(estadoCuentaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
