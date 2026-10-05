package com.universidad.model.service;

import com.universidad.model.Curso;
import com.universidad.model.Estudiante;

public interface IServicioEvaluacion {
    void registrarNota(Estudiante estudiante, Curso curso, double nota);
    void registrarAsistencia(Estudiante estudiante, Curso curso);
}
