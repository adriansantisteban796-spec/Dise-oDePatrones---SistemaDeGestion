package com.universidad.patrones.Comportamiento.memento;

public class MatriculaMemento {
    private final String estadoSnapshot;

    public MatriculaMemento(String estadoSnapshot) {
        this.estadoSnapshot = estadoSnapshot;
    }

    public String getEstadoSnapshot() { return estadoSnapshot; }
}