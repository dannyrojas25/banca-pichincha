package com.banca.pichincha.repositorio;

import com.banca.pichincha.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query(value = "select nombre from personas p where p.id = :id", nativeQuery = true)
    String obtenerNombrePersona(@Param("id") long id);

    @Query(value = "select * from cuentas c where c.nombre_persona = :nombrePersona", nativeQuery = true)
    List<Cuenta> obtenerCuentasPorNombreCliente(@Param("nombrePersona") String nombrePersona);
}
