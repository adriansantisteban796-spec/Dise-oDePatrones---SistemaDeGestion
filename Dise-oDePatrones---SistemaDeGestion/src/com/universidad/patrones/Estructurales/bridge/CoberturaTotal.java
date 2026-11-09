package com.universidad.patrones.Estructurales.bridge;

public class CoberturaTotal implements ICobertura {
    @Override 
    public String aplicarCobertura(){
        return "Cobertura total del 100% en tramites y segurto medico.";
    }
}
