package com.universidad.model;

import java.util.Date;


public class Matricula {
    private int idMatricula;
    private Estudiante estudiante;
    private Curso curso;
    private String estado;
    private Date fechaRegistro;

    public Matricula(int idMatricula, Estudiante estudiante, Curso curso, String estado, Date fechaRegistro) {
        this.idMatricula = idMatricula;
        this.estudiante = estudiante;
        this.curso = curso;
        this.estado = "PENDIENTE";
        this.fechaRegistro = fechaRegistro;
    }

    public void confirmar(){
        this.estado = "CONFIRMADA";
        this.curso.reducirVacantes();
    }

    public void anular(){
        this.estado = "ANULADA";
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    
}