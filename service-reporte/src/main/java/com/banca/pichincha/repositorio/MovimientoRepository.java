package com.banca.pichincha.repositorio;

import com.banca.pichincha.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query(value = "select * from movimientos m where m.cuenta_id = :id and m.fecha between :fechaInicio and :fechaFin", nativeQuery = true)
    List<Movimiento> obtenerMovimientosPorCuentaYFechas(@Param("id") Long id, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);
}
