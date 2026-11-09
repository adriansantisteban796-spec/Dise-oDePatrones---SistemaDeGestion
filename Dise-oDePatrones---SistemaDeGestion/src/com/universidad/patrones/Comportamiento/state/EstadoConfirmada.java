package com.universidad.patrones.Comportamiento.state;

public class EstadoConfirmada implements EstadoMatricula {
    @Override
    public void avanzar(MatriculaContext contexto) {
        System.out.println("[STATE] La matrícula ya está CONFIRMADA. Estado final alcanzado.");
    }

    @Override
    public String getNombreEstado() { return "CONFIRMADA"; }
}