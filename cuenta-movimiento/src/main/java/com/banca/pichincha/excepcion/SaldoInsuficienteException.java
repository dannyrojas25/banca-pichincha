package com.banca.pichincha.excepcion;

public class SaldoInsuficienteException extends RuntimeException{

    public SaldoInsuficienteException() {
        super("Saldo no disponible");
    }

}
