package com.universidad.service;

import com.universidad.model.Curso;
import com.universidad.model.Estudiante;

public interface IServicioMatricula {
    boolean matricularCurso(Estudiante estudiante, Curso curso);
    void anularMatricula(int idMatricula);
}
