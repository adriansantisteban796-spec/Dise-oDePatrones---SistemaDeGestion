package com.universidad.model;



public class PlanEstudio {
    private String carrera;
    private int aVigencia;
    private int creditosTotales;
    private boolean requiereTesis;
    
    public PlanEstudio(String carrera, int aVigencia, int creditosTotales, boolean requiereTesis) {
        this.carrera = carrera;
        this.aVigencia = aVigencia;
        this.creditosTotales = creditosTotales;
        this.requiereTesis = requiereTesis;
    }
    
    
}
