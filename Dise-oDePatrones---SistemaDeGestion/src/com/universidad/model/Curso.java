package com.universidad.model;

public class Curso {
    private int idCurso;
    private String nombre;
    private int creditos;
    private int vacantesDisponibles;

    public Curso(int idCurso, String nombre, int creditos, int vacantesDisponibles) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.creditos = creditos;
        this.vacantesDisponibles = vacantesDisponibles;
    }

    public boolean tieneVacantes() {
        return vacantesDisponibles > 0;
    }

    // MÉTODO QUE FALTABA
    public void reducirVacante() {
        if (vacantesDisponibles > 0) {
            vacantesDisponibles--;
        }
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getVacantesDisponibles() {
        return vacantesDisponibles;
    }
}