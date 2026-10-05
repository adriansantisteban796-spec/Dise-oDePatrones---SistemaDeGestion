package com.universidad.patrones.Creacionales.Prototype;

public class PlantillaS {
    private String codigoCurso;
    private String estructuraContenido;

    public PlantillaS(String codigoCurso, String estructuraContenido) {
        this.codigoCurso = codigoCurso;
        this.estructuraContenido = estructuraContenido;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getEstructuraContenido() {
        return estructuraContenido;
    }

    public void setEstructuraContenido(String estructuraContenido) {
        this.estructuraContenido = estructuraContenido;
    }

    @Override 
    public PlantillaS clone(){
        try{
            return (PlantillaS) super.clone();

        }catch(CloneNotSupportedException e){
                return new PlantillaS(this.codigoCurso, this.estructuraContenido);
        }
    }

    
}
