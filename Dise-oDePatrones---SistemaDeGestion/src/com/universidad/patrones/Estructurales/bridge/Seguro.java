package com.universidad.patrones.Estructurales.bridge;

public abstract class Seguro {
    protected ICobertura cobertura;

    public Seguro(ICobertura cobertura) {
        this.cobertura = cobertura;
    }

    public abstract String obtenerDetalles();
}