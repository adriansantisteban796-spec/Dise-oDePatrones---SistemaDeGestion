package com.universidad.patrones.Estructurales.decorator;

public class CursoBase implements ICursoCosteable {
    private String nombre;
    private double costoBase;

    public CursoBase(String nombre, double costoBase) {
        this.nombre = nombre;
        this.costoBase = costoBase;
    }

    @Override
    public String getDescripcion() {
        return "Curso: " + nombre;
    }

    @Override
    public double getCosto() {
        return costoBase;
    }
}