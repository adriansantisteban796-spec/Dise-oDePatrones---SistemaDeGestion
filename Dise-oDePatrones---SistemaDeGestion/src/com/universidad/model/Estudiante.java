package com.universidad.model;

public class Estudiante extends Usuario {
    private boolean tieneDeuda;

    public Estudiante(int id, String nombre, String codigo, boolean tieneDeuda) {
        super(id, nombre, codigo);
        this.tieneDeuda = tieneDeuda;
    }
    
    public boolean tieneDeuda(){
        return tieneDeuda;
    }

    public void setTieneDeuda(boolean tieneDeuda){
        this.tieneDeuda = tieneDeuda;
        
    }
}
