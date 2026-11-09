package com.universidad.patrones.Comportamiento.state;

public interface EstadoMatricula {
    void avanzar(MatriculaContext contexto);
    String getNombreEstado();
}
