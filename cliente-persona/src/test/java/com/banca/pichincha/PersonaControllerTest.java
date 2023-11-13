package com.banca.pichincha;

import com.banca.pichincha.controlador.PersonaController;
import com.banca.pichincha.modelo.Cliente;
import com.banca.pichincha.modelo.Persona;
import com.banca.pichincha.repositorio.PersonaRepository;
import com.banca.pichincha.servicio.PersonaService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PersonaControllerTest {

    @Mock
    private PersonaService personaService;

    @Mock
    private PersonaRepository personaRepository;

    @InjectMocks
    private PersonaController personaController;

    @Test
    public void testObtenerPersonaExistente() {
        Persona persona = Persona.builder()
                .id(1L)
                .nombre("Persona prueba")
                .genero("F")
                .edad("24")
                .identificacion("125478963")
                .direccion("Av 123 # 4-56")
                .telefono("3256987410")
                .cliente(Cliente.builder()
                        .id(6L)
                        .contrasenia("5896")
                        .estado(true)
                        .build())
                .build();

        when(personaService.obtenerPersonaPorId(persona.getId())).thenReturn(persona);

        ResponseEntity<Persona> respuesta = personaController.obtenerPersonaPorId(persona.getId());

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(persona, respuesta.getBody());
    }

    @Test
    public void testObtenerPersonaNoExistente() {
        Long idPersonaNoExistente = 8L;

        when(personaService.obtenerPersonaPorId(idPersonaNoExistente)).thenReturn(null);

        ResponseEntity<Persona> respuesta = personaController.obtenerPersonaPorId(idPersonaNoExistente);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertNull(respuesta.getBody());
    }


    @Test
    public void testCrearPersonaExitoso() {
        Persona persona = Persona.builder()
                .id(1L)
                .nombre("Persona prueba")
                .genero("F")
                .edad("24")
                .identificacion("125478963")
                .direccion("Av 123 # 4-56")
                .telefono("3256987410")
                .cliente(Cliente.builder()
                        .id(6L)
                        .contrasenia("5896")
                        .estado(true)
                        .build())
                .build();

        when(personaRepository.save(persona)).thenReturn(persona);

        ResponseEntity<String> respuesta = personaController.crearPersona(persona);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().contains("Éxito"));
    }

    @Test
    public void testCrearPersonaError() {
        Persona persona = Persona.builder()
                .id(1L)
                .nombre("Persona prueba")
                .genero("F")
                .edad("24")
                .identificacion("125478963")
                .direccion("Av 123 # 4-56")
                .telefono("3256987410")
                .cliente(Cliente.builder()
                        .id(6L)
                        .contrasenia("5896")
                        .estado(true)
                        .build())
                .build();

        when(personaRepository.save(persona)).thenReturn(null);

        ResponseEntity<String> respuesta = personaController.crearPersona(persona);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().contains("Error"));
    }
}
