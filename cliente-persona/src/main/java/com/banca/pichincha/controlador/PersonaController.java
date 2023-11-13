package com.banca.pichincha.controlador;

import com.banca.pichincha.modelo.Persona;
import com.banca.pichincha.repositorio.PersonaRepository;
import com.banca.pichincha.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/clientes-personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/obtener-persona/{id}")
    public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long id){
        Persona persona = personaService.obtenerPersonaPorId(id);
            if(persona != null){
                return ResponseEntity.ok(persona);
            }else {
                return ResponseEntity.notFound().build();
            }
    }

    @GetMapping("/obtener-personas")
    public ResponseEntity<List<Persona>> obtenerPersonas() {
        try {
            List<Persona> personas = personaRepository.findAll();
            return ResponseEntity.ok(personas);
        }catch(RuntimeException e){
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/crear-persona")
    public ResponseEntity<String> crearPersona(@RequestBody Persona persona){
        Persona personaRegistrada = personaRepository.save(persona);
        if(personaRegistrada != null){
            return ResponseEntity.ok("Éxito. ID de la persona creada: " + personaRegistrada.getId());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la persona.");
        }
    }

    @PutMapping("/actualizar-persona/{id}")
    public ResponseEntity<Persona> actualizarPersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        Optional<Persona> personaExistente = personaRepository.findById(id);
        if (personaExistente.isPresent()) {
            personaActualizada.setId(id);
            return ResponseEntity.ok(personaRepository.save(personaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/borrar-persona/{id}")
    public ResponseEntity<String> eliminarPersona(@PathVariable Long id) {
        if(personaRepository.existsById(id)){
            personaRepository.deleteById(id);
        }else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Éxito. ID de la persona eliminada: " + id);
    }
}
