package com.universidad.model;

public class PlanEstudio {
    private String carrera;
    private int anioVigencia;
    private int creditosTotales;
    private boolean requiereTesis;

    private PlanEstudio(Builder builder) {
        this.carrera = builder.carrera;
        this.anioVigencia = builder.anioVigencia;
        this.creditosTotales = builder.creditosTotales;
        this.requiereTesis = builder.requiereTesis;
    }

    public String getCarrera() {
        return carrera;
    }

    public int getAnioVigencia() {
        return anioVigencia;
    }

    public int getCreditosTotales() {
        return creditosTotales;
    }

    public boolean isRequiereTesis() {
        return requiereTesis;
    }

    public static class Builder {
        private String carrera;
        private int anioVigencia;
        private int creditosTotales;
        private boolean requiereTesis = true;

        public Builder(String carrera, int anioVigencia) {
            this.carrera = carrera;
            this.anioVigencia = anioVigencia;
        }

        public Builder setCreditosTotales(int creditosTotales) {
            this.creditosTotales = creditosTotales;
            return this;
        }

        public Builder setRequiereTesis(boolean requiereTesis) {
            this.requiereTesis = requiereTesis;
            return this;
        }

        public PlanEstudio build() {
            return new PlanEstudio(this);
        }
    }
}