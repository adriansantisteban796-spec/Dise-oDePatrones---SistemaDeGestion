package com.universidad.patrones.Comportamiento.state;

public class MatriculaContext {
    private EstadoMatricula estadoActual;

    public MatriculaContext() {
        this.estadoActual = new EstadoSolicitada();
    }

    public void setEstado(EstadoMatricula estado) { this.estadoActual = estado; }
    public void avanzarEstado() { estadoActual.avanzar(this); }
    public String getEstadoNombre() { return estadoActual.getNombreEstado(); }
}