package com.banca.pichincha.repositorio;

import com.banca.pichincha.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query(value = "select * from cuentas c where c.numero_cuenta = :numeroCuenta", nativeQuery = true)
    List<Movimiento> obtenerMovimientosPorCuenta(@Param("numeroCuenta") String numeroCuenta);
}
