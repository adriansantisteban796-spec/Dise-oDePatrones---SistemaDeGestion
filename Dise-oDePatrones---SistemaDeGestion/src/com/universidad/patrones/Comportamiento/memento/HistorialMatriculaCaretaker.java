package com.universidad.patrones.Comportamiento.memento;

import java.util.Stack;

public class HistorialMatriculaCaretaker {
    private final Stack<MatriculaMemento> historial = new Stack<>();

    public void guardarSnapshot(MatriculaMemento memento) {
        historial.push(memento);
        System.out.println("[MEMENTO] Snapshot de auditoría respaldado exitosamente.");
    }

    public MatriculaMemento deshacer() {
        if (!historial.isEmpty()) {
            return historial.pop();
        }
        return null;
    }
}