package com.universidad.patrones.Comportamiento.observer;


import java.util.ArrayList;
import java.util.List;

public class CanalNotificacionSubject {
    private final List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador o) { observadores.add(o); }

    public void notificarTodos(String mensaje) {
        for (Observador o : observadores) {
            o.actualizar(mensaje);
        }
    }
}