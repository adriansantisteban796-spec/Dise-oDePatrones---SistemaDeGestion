package com.universidad.patrones.Estructurales.adapter;

public class SistemaPagosExterno {
    public boolean realizarCobroSoles(String codigoAlumno, double montoEnSoles){
        System.out.println("Procesando cobro de S/. " + montoEnSoles + " para el codigo " +codigoAlumno);
        return true;
    }
}
