package com.universidad.patrones.Comportamiento.command;

import com.universidad.model.Curso;
import com.universidad.model.Estudiante;
import com.universidad.service.IServicioMatricula;

public class RegistrarMatriculaCommand implements Command {
    private final IServicioMatricula servicio;
    private final Estudiante estudiante;
    private final Curso curso;

    public RegistrarMatriculaCommand(IServicioMatricula servicio, Estudiante estudiante, Curso curso) {
        this.servicio = servicio;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    @Override
    public void execute() {
        System.out.println("[COMMAND] Ejecutando orden de matriculación...");
        servicio.matricularCurso(estudiante, curso);
    }
}