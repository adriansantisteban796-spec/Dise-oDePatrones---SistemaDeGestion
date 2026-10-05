package com.universidad.patrones.Creacionales.Factorymethod;

public class PresencialFactory extends ModalidadFactory{
    @Override 
    public Modalidad crearModalidad(){
        return new ModalidadPresencial();
    }
}
