package com.universidad.patrones.Creacionales.Builder;

import com.universidad.model.PlanEstudio;

public class PlanEstudioBuilder {
    private String carrera;
    private int aVigencia;
    private int creditosTotales;
    private boolean requiereTesis = true;


    public PlanEstudioBuilder(String carrera, int aVigencia) {
        this.carrera = carrera;
        this.aVigencia = aVigencia;
    }
    
    public PlanEstudioBuilder setCreditosTotales(int creditosTotales){
        this.creditosTotales = creditosTotales;
        return this;
    }
    public PlanEstudioBuilder setRequiereTesis(boolean requiereTesis){
        this.requiereTesis = requiereTesis;
        return this;
    }
    public PlanEstudio build() {
        return new PlanEstudio.Builder(carrera, aVigencia)
                .setCreditosTotales(creditosTotales)
                .setRequiereTesis(requiereTesis)
                .build();
    }


    
}
