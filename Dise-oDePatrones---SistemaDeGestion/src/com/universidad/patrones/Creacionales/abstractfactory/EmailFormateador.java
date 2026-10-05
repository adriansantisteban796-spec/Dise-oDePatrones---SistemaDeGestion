package com.universidad.patrones.Creacionales.abstractfactory;

public class EmailFormateador implements IFormateador {
    @Override
    public String formatearContenido(String texto) {
        return "Aviso Institucional UTP" + texto + ".";
    }
}
