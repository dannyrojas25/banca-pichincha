package com.banca.pichincha.convertidor;

import com.banca.pichincha.dto.CuentaDTO;
import com.banca.pichincha.modelo.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class ConverterToCuentaDTO {
    public static CuentaDTO fromEntity(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.isEstado());

        return cuentaDTO;
    }

}
