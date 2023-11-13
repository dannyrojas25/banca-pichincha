package com.banca.pichincha.dto;

import com.banca.pichincha.modelo.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDTO {

    private Long id;

    private String numeroCuenta;

    private String tipoCuenta;

    private int saldoInicial;

    private boolean estado;

    private List<MovimientoDTO> movimientos;

    private String nombrePersona;

    private int saldoDisponible;

}
