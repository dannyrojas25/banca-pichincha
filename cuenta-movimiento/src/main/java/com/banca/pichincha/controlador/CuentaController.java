package com.banca.pichincha.controlador;

import com.banca.pichincha.modelo.Cuenta;
import com.banca.pichincha.repositorio.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/cuentas-movimientos")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping("/obtener-cuenta/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear-cuenta")
    public ResponseEntity<String> crearCuenta(@RequestBody Cuenta cuenta) {
        Cuenta cuentaRegistrada = cuentaRepository.save(cuenta);
        if(cuentaRegistrada != null){
            return ResponseEntity.ok("Éxito. ID de la cuenta creada: " + cuentaRegistrada.getId());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la cuenta.");
        }
    }

    @PutMapping("/actualizar-cuenta/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaActualizada) {
        Optional<Cuenta> cuentaExistente = cuentaRepository.findById(id);
        if (cuentaExistente.isPresent()) {
            cuentaActualizada.setId(id);
            return ResponseEntity.ok(cuentaRepository.save(cuentaActualizada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/borrar-cuenta/{id}")
    public ResponseEntity<String> eliminarCuenta(@PathVariable Long id) {
        if(cuentaRepository.existsById(id)){
            cuentaRepository.deleteById(id);
        }else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Éxito. ID de la cuenta eliminada: " + id);
    }
}
