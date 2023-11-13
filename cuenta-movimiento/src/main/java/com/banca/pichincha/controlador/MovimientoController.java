package com.banca.pichincha.controlador;

import com.banca.pichincha.excepcion.SaldoInsuficienteException;
import com.banca.pichincha.modelo.Cuenta;
import com.banca.pichincha.modelo.Movimiento;
import com.banca.pichincha.repositorio.CuentaRepository;
import com.banca.pichincha.repositorio.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cuentas-movimientos")
public class MovimientoController {

    private final String clientePersonaBaseUrl = "http://localhost:8081/clientes-personas";

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @GetMapping("/obtener-movimientos")
    public ResponseEntity<List<Movimiento>> obtenerMovmientos() {
        try {
            List<Movimiento> movimientos = movimientoRepository.findAll();
            return ResponseEntity.ok(movimientos);
        }catch(RuntimeException e){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/obtener-movimiento/{id}")
    public ResponseEntity<Movimiento> obtenerMovimeintoPorId(@PathVariable Long id){
        Optional<Movimiento> movimiento = movimientoRepository.findById(id);
        return movimiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear-movimiento")
    public ResponseEntity<String> crearMovimiento(@RequestBody Movimiento movimiento){

        try {
            if (movimiento.getValor() == 0) {
                return ResponseEntity.badRequest().build();
            }

            Cuenta cuenta = movimiento.getCuenta();
            int saldoActual = cuenta.getSaldoInicial();
            int valorMovimiento = movimiento.getValor();

            if (saldoActual + valorMovimiento < 0) {
                throw new SaldoInsuficienteException();
            }

            // Actualizar el saldo de la cuenta
            cuenta.setSaldoInicial(saldoActual + valorMovimiento);
            cuentaRepository.save(cuenta);

            // Registrar la transacción
            movimiento.setFecha(new Date());
            Movimiento nuevoMovimiento = movimientoRepository.save(movimiento);

            return ResponseEntity.ok("Éxito. ID del movimiento: " + nuevoMovimiento.getId());

        } catch (SaldoInsuficienteException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Saldo no disponible");
        }
    }

    @PutMapping("/actualizar-movimiento/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(@PathVariable Long id, @RequestBody Movimiento movimientoActualizado) {
        Optional<Movimiento> movimientoExistente = movimientoRepository.findById(id);
        if (movimientoExistente.isPresent()) {
            movimientoActualizado.setId(id);
            return ResponseEntity.ok(movimientoRepository.save(movimientoActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/borrar-movimiento/{id}")
    public ResponseEntity<String> eliminarMovimiento(@PathVariable Long id) {
        if(movimientoRepository.existsById(id)){
            movimientoRepository.deleteById(id);
        }else{
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Éxito. ID del movimiento eliminado: " + id);
    }
}
