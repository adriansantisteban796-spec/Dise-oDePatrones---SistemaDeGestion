package com.universidad.patrones.Estructurales.bridge;

public class SeguroConvenio extends Seguro {
    public SeguroConvenio(ICobertura cobertura) {
        super(cobertura);
    }

    @Override
    public String obtenerDetalles() {
        return "Seguro por Convenio UTP -> " + cobertura.aplicarCobertura();
    }
}