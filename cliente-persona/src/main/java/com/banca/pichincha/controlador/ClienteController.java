package com.banca.pichincha.controlador;

import com.banca.pichincha.modelo.Cliente;
import com.banca.pichincha.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/clientes-personas")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/obtener-cliente/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear-cliente")
    public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente) {
        Cliente clienteRegistrado = clienteRepository.save(cliente);
        if(clienteRegistrado != null){
            return ResponseEntity.ok("Éxito. ID del cliente creado: " + clienteRegistrado.getId());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el cliente.");
        }
    }

    @PutMapping("/actualizar-cliente/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            clienteActualizado.setId(id);
            return ResponseEntity.ok(clienteRepository.save(clienteActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/borrar-cliente/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
        }else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Éxito. ID de la persona eliminada: " + id);
    }
}
