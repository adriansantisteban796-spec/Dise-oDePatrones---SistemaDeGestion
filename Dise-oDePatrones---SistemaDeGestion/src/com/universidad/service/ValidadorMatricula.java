package com.universidad.service;

import com.universidad.model.Curso;
import com.universidad.model.Estudiante;

public class ValidadorMatricula {

    public boolean validarRequisitos(Estudiante estudiante, Curso curso) {
        if (estudiante.tieneDeuda()) {
            System.out.println("[VALIDACIÓN FALLIDA] El estudiante " + estudiante.getNombre() + " presenta deudas pendientes.");
            return false;
        }
        if (!curso.tieneVacantes()) {
            System.out.println("[VALIDACIÓN FALLIDA] El curso " + curso.getNombre() + " no tiene vacantes disponibles.");
            return false;
        }
        System.out.println("[VALIDACIÓN EXITOSA] El estudiante cumple con los requisitos para matricularse en " + curso.getNombre() + ".");
        return true;
    }
}