package com.universidad.patrones.Creacionales.Factorymethod;

import com.universidad.model.Curso;

public class ModalidadPresencial extends Modalidad {
    public ModalidadPresencial(){
        super("Presencial");
    }

    @Override 
    public void programarClase(Curso curso){
        System.out.println("Asignando pabellon y aula fisica para el curso" + curso.getNombre());
    }
}
