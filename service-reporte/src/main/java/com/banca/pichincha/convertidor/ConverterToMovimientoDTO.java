package com.banca.pichincha.convertidor;

import com.banca.pichincha.dto.MovimientoDTO;
import com.banca.pichincha.modelo.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class ConverterToMovimientoDTO {
    public static MovimientoDTO fromEntity(Movimiento movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimiento.getId());
        movimientoDTO.setFecha(movimiento.getFecha());
        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoDTO.setValor(movimiento.getValor());
        movimientoDTO.setSaldo(movimiento.getSaldo());
        movimientoDTO.setDescripcion(movimiento.getDescripcion());

        return movimientoDTO;
    }
}
