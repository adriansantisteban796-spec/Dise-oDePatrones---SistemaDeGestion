package com.universidad.patrones.Comportamiento.state;

public class EstadoSolicitada implements EstadoMatricula {
    @Override
    public void avanzar(MatriculaContext contexto) {
        System.out.println("[STATE] La matrícula pasó de SOLICITADA a CONFIRMADA.");
        contexto.setEstado(new EstadoConfirmada());
    }

    @Override
    public String getNombreEstado() { return "SOLICITADA"; }
}