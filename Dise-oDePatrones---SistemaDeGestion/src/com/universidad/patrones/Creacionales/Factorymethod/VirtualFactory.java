package com.universidad.patrones.Creacionales.Factorymethod;

public class VirtualFactory extends ModalidadFactory {
    @Override
    public Modalidad crearModalidad() {
        return new ModalidadVirtual();
    }
}
