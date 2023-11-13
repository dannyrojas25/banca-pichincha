package com.banca.pichincha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class EstadoCuentaDTO {
    private List<CuentaDTO> cuentas;
}
