package com.universidad.patrones.Estructurales.composite;

public abstract class ComponenteEstructura {
    protected String nombre;

    public ComponenteEstructura(String nombre){
        this.nombre = nombre;
    }
    public abstract int obtenerCreditos();
    public abstract void mostrarDetalle(String sangris);
}
