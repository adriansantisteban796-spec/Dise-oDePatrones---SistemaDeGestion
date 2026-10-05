package com.universidad.patrones.Creacionales.abstractfactory;

public interface ComunicacionFactory {
    INotificador crearNotificador();
    IFormateador crearFormateador();
}
