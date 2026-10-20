package com.universidad.patrones.Estructurales.facade;

import com.universidad.model.Curso;
import com.universidad.model.Estudiante;
import com.universidad.patrones.solid.INotificacionService;
import com.universidad.service.AuditoriaService;
import com.universidad.service.MatriculaService;
import com.universidad.service.ValidadorMatricula;

public class MatriculaFacade {
    private final MatriculaService matriculaService;

    public MatriculaFacade(INotificacionService notificador){
        ValidadorMatricula validador = new ValidadorMatricula();
        AuditoriaService auditoria = new AuditoriaService();
        this.matriculaService = new MatriculaService(validador, auditoria, notificador);

    }

    public boolean procesarMatriculaCompleta(Estudiante estudiante, Curso curso){
        System.out.println("Iniciando proceso unificado de matricula");
        return matriculaService.matricularCurso(estudiante, curso);
    }
}
