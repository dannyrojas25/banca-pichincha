package com.banca.pichincha.dto;

import com.banca.pichincha.modelo.Cuenta;
import com.banca.pichincha.modelo.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private Long id;

    private Date fecha;

    private String tipoMovimiento;

    private int valor;

    private int saldo;

    private String descripcion;

}
