package com.banca.pichincha.repositorio;

import com.banca.pichincha.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query(value = "select * from personas p where p.id = :id", nativeQuery = true)
    Persona buscarPersonaPorId(@Param("id") long id);
}
