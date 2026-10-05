package com.universidad.patrones.Creacionales.Factorymethod;

import com.universidad.model.Curso;

public abstract class Modalidad {
    private String nombre;

    public Modalidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void programarClase(Curso curso);
}
