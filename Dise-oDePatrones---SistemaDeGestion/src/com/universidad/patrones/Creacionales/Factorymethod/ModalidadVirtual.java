package com.universidad.patrones.Creacionales.Factorymethod;

import com.universidad.model.Curso;

public class ModalidadVirtual extends Modalidad {
    public ModalidadVirtual(){
        super("Virtual");
    }

    @Override 
    public void programarClase(Curso curso){
        System.out.println("Generando enlace de reunion remota y sincronizando con Aula Virtual para: " +curso.getNombre());
    }
}
