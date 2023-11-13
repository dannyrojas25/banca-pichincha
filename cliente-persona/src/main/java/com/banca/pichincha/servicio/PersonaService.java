package com.banca.pichincha.servicio;

import com.banca.pichincha.modelo.Persona;
import com.banca.pichincha.repositorio.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    public Persona obtenerPersonaPorId(Long id){
        return personaRepository.buscarPersonaPorId(id);
    }
}
