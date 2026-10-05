package com.universidad.model;

public class Docente extends Usuario {
    private String especialidad;

    public Docente(int id, String nombre, String codigo, String especialidad) {
        super(id, nombre, codigo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad(){
        return especialidad;
    }
    

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    
}
