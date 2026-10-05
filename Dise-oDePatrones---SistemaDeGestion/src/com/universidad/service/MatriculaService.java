package com.universidad.service;

import com.universidad.model.Curso;
import com.universidad.model.Estudiante;
import com.universidad.model.Matricula;
import com.universidad.patrones.solid.INotificacionService;

public class MatriculaService implements IServicioMatricula {

    private final ValidadorMatricula validador;
    private final AuditoriaService auditoria;
    private final INotificacionService notificador;

    // Inyección de dependencias por constructor (DIP)
    public MatriculaService(ValidadorMatricula validador, AuditoriaService auditoria, INotificacionService notificador) {
        this.validador = validador;
        this.auditoria = auditoria;
        this.notificador = notificador;
    }

    @Override
    public boolean matricularCurso(Estudiante estudiante, Curso curso) {
        if (!validador.validarRequisitos(estudiante, curso)) {
            auditoria.registrarAccion(estudiante.getCodigo(), "INTENTO FALLIDO DE MATRÍCULA en " + curso.getNombre());
            return false;
        }

        Matricula matricula = new Matricula(1, estudiante, curso);
        matricula.confirmar();

        auditoria.registrarAccion(estudiante.getCodigo(), "MATRÍCULA CONFIRMADA en " + curso.getNombre());
        notificador.enviarMensaje(estudiante.getCodigo(), "Se ha registrado con éxito su matrícula en " + curso.getNombre());

        return true;
    }

    @Override
    public void anularMatricula(int idMatricula) {
        auditoria.registrarAccion("SISTEMA", "ANULACIÓN DE MATRÍCULA ID: " + idMatricula);
        System.out.println("La matrícula ID " + idMatricula + " ha sido anulada.");
    }
}