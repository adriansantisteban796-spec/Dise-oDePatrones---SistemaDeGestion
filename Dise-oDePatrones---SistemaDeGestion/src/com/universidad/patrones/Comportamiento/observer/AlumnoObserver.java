package com.universidad.patrones.Comportamiento.observer;

public class AlumnoObserver implements Observador {
    private final String codigoAlumno;

    public AlumnoObserver(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("[OBSERVER] Alumno (" + codigoAlumno + ") Notificación: " + mensaje);
    }
}