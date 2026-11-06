package com.universidad.patrones.Estructurales.decorator;

public abstract class CursoDecorator implements ICursoCosteable {
    protected ICursoCosteable cursoDecorado;

    public CursoDecorator(ICursoCosteable cursoDecorado) {
        this.cursoDecorado = cursoDecorado;
    }

    @Override
    public String getDescripcion() {
        return cursoDecorado.getDescripcion();
    }

    @Override
    public double getCosto() {
        return cursoDecorado.getCosto();
    }
}