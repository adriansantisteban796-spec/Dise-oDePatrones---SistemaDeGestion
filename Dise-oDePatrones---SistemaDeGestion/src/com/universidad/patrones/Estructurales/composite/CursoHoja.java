package com.universidad.patrones.Estructurales.composite;

public class CursoHoja extends ComponenteEstructura {
    private final int creditos;

    public CursoHoja(String nombre, int creditos) {
        super(nombre);
        this.creditos = creditos;
    }

    @Override 
    public int obtenerCreditos(){
        return creditos;
    }

    @Override 
    public void mostrarDetalle(String sangria){
        System.out.println(sangria + "- Curso: " + nombre + " (" + creditos + " créditos)");
    }
}
