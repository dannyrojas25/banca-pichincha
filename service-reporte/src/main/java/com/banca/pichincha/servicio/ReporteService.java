package com.banca.pichincha.servicio;

import com.banca.pichincha.convertidor.ConverterToCuentaDTO;
import com.banca.pichincha.convertidor.ConverterToMovimientoDTO;
import com.banca.pichincha.dto.CuentaDTO;
import com.banca.pichincha.dto.EstadoCuentaDTO;
import com.banca.pichincha.dto.MovimientoDTO;
import com.banca.pichincha.modelo.Cuenta;
import com.banca.pichincha.modelo.Movimiento;
import com.banca.pichincha.repositorio.CuentaRepository;
import com.banca.pichincha.repositorio.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReporteService {

    private CuentaDTO cuentaAConvertir = new CuentaDTO();
    private MovimientoDTO movimientoAConvertir = new MovimientoDTO();

    @Autowired
    MovimientoRepository movimientoRepository;

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    ConverterToCuentaDTO converterToCuentaDTO;

    @Autowired
    ConverterToMovimientoDTO converterToMovimientoDTO;

    public EstadoCuentaDTO generarReporte(Long id, Date fechaInicio, Date fechaFin) {
        String nombrePersona = cuentaRepository.obtenerNombrePersona(id);

        if (nombrePersona == null) {
            return null;
        }

        List<Cuenta> cuentas = cuentaRepository.obtenerCuentasPorNombreCliente(nombrePersona);
        List<CuentaDTO> cuentasDTO = new ArrayList<>();

        // Para cada cuenta, obtener los movimientos en el rango de fechas
        cuentas.forEach(cuenta -> {
            List<MovimientoDTO> movimientosDTO = new ArrayList<>();
            cuentaAConvertir = converterToCuentaDTO.fromEntity(cuenta);
            List<Movimiento> movimientos = movimientoRepository.obtenerMovimientosPorCuentaYFechas(cuenta.getId(), fechaInicio, fechaFin);
            movimientos.forEach(movimiento -> {
                movimientoAConvertir = converterToMovimientoDTO.fromEntity(movimiento);
                cuentaAConvertir.setSaldoDisponible(
                        movimiento.getDescripcion().equals("Retiro") ?
                                cuenta.getSaldoInicial() - movimiento.getValor() :
                                cuenta.getSaldoInicial() + movimiento.getValor()
                );
                movimientosDTO.add(movimientoAConvertir);
                cuentasDTO.add(cuentaAConvertir);
            });
            cuentaAConvertir.setMovimientos(movimientosDTO);
            cuentaAConvertir.setNombrePersona(nombrePersona);
        });
        // Crear y retornar el DTO del estado de cuenta
        EstadoCuentaDTO estadoCuentaDTO = new EstadoCuentaDTO();
        estadoCuentaDTO.setCuentas(cuentasDTO);

        return estadoCuentaDTO;
    }
}
